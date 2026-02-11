package com.matheusgondra.books.auth.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TokenServiceTest {
    private final String secretKey = "testSecretKey";
    private TokenService sut;

    @BeforeEach
    void setUp() {
        sut = new TokenService(secretKey);
    }

    @Test
    void shouldGenerateTokenJWT() {
        String payload = "testPayload";

        String token = sut.generateToken(payload);

        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3);
    }
}