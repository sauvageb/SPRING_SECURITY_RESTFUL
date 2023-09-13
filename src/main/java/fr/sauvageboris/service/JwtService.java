package fr.sauvageboris.service;

import fr.sauvageboris.repository.entity.User;

import java.util.Map;

public interface JwtService {

    String generateToken(Map<String, Object> extraClaims, User user);

    String generateToken(User userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String token);
}
