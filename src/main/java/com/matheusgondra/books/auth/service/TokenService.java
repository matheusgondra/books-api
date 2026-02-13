package com.matheusgondra.books.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Service
public class TokenService {
    private final String secretKey;

    public TokenService(@Value("${api.jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken(String payload) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.create()
                    .withSubject(payload)
                    .withIssuer("books-api")
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException | IllegalArgumentException ex) {
            return null;
        }
    }

    private Instant getExpirationDate() {
        return Instant.now().plus(1, ChronoUnit.DAYS);
    }
}
