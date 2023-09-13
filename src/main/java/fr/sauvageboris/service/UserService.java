package fr.sauvageboris.service;

import org.springframework.security.core.userdetails.UserDetailsService;

// Interface pour le service utilisateur
public interface UserService {

    // Méthode pour obtenir le service de détails de l'utilisateur
    UserDetailsService userDetailsService();
}
