package fr.sauvageboris.security;

import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityConstants {

    public static final String JWT_SECRET = "mypasswordmypasswordmypasswordmypasswordmypasswordmypasswordmypasswordmypasswordmypassword";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    public static final String SIGN_IN_URI_ENDING = "/api/auth/signin";
    public static final String SIGN_UP_URI_ENDING = "/api/auth/signup";
}
