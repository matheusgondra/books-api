package com.matheusgondra.books.cryptography.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {
    private TokenService sut;

    @BeforeEach
    void setUp() {
        String secretKey = "testSecretKey";
        sut = new TokenService(secretKey);
    }

    @Test
    void shouldGenerateTokenJWT() {
        String payload = "testPayload";

        String token = sut.generateToken(payload);

        assertNotNull(token);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void shouldReturnNullWhenTokenCreationFails() {
        TokenService invalidSut = new TokenService("");

        String token = invalidSut.generateToken("testPayload");

        assertNull(token);
    }
}