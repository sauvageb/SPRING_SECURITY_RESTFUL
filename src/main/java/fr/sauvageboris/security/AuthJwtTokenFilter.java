package fr.sauvageboris.security;


import fr.sauvageboris.repository.entity.User;
import fr.sauvageboris.service.JwtService;
import fr.sauvageboris.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AuthJwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        logger.info("Request URI: " + uri);

        try {
            String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            logger.info("Authorization Header value: " + headerToken);

            if (headerToken == null || !headerToken.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
                this.logger.trace("No Authorization header found!");
                filterChain.doFilter(request, response);
                return;
            }

            if (headerToken.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX) && !uri.endsWith(SecurityConstants.SIGN_IN_URI_ENDING)) {
                headerToken = StringUtils.delete(headerToken, SecurityConstants.BEARER_TOKEN_PREFIX).trim();
                if (jwtService.isTokenValid(headerToken)) {
                    String username = jwtService.extractUserName(headerToken);
                    this.logger.trace(LogMessage.format("username '%s' extracted from Bearer Authorization header", username));

                    //User is an org.springframework.security.core.userdetails.User object
                    User userDetails = (User) userService.userDetailsService().loadUserByUsername(username);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        logger.info("(Authorized) Authentication: " + authenticationToken.toString());
                    }
                }
            }
        } catch (AuthenticationException ex) {
            this.logger.info("Failed to process authentication request: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }

}
