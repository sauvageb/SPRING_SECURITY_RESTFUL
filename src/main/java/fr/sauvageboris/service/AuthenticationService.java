package fr.sauvageboris.service;

import fr.sauvageboris.dto.request.SignUpRequest;
import fr.sauvageboris.dto.request.SigninRequest;
import fr.sauvageboris.dto.response.JwtAuthenticationResponse;

// Interface pour le service d'authentification
public interface AuthenticationService {

    // Méthode pour l'inscription d'un utilisateur
    JwtAuthenticationResponse signup(SignUpRequest request);

    // Méthode pour la connexion d'un utilisateur
    JwtAuthenticationResponse signin(SigninRequest request);
}
