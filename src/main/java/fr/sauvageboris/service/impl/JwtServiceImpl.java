package fr.sauvageboris.service.impl;

import fr.sauvageboris.repository.entity.User;
import fr.sauvageboris.security.SecurityConstants;
import fr.sauvageboris.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

//    @Value("${token.signing.key}")
//    private String jwtSigningKey;
//    @Value("${token.signin.expiration.duration}")
//    private long expirationMs;

    @Override
    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(getSigningKey(), SecurityConstants.SIGNATURE_ALGORITHM)
                .compact();
    }

    @Override
    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT signature: {} " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token: {} " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is expired: {} " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT token is unsupported: {} " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new AuthenticationCredentialsNotFoundException("JWT claims string is empty: {} " + e.getMessage());
        }
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
        Key key = new SecretKeySpec(keyBytes, SecurityConstants.SIGNATURE_ALGORITHM.getJcaName());
        return key;
    }

}
