package fr.sauvageboris.service.impl;

import fr.sauvageboris.repository.UserRepository;
import fr.sauvageboris.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// Implémentation du service utilisateur
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                // Recherche un utilisateur par username
                UserDetails user = userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("username " + username + " not found"));
                return user;
            }
        };
    }
}
