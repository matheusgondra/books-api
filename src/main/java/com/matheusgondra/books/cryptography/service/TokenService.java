package com.matheusgondra.books.cryptography.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Service
public class TokenService {
    private final Algorithm algorithm;

    public TokenService(@Value("${api.jwt.secret}") String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public String generateToken(String payload) {
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

    public String validateToken(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer("books-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    private Instant getExpirationDate() {
        return Instant.now().plus(1, ChronoUnit.DAYS);
    }
}
