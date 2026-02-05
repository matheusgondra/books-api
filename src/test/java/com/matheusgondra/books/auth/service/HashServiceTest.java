package com.matheusgondra.books.auth.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HashServiceTest {
    private final HashService sut = new HashService();

    @Test
    void shouldReturnHashedValue() {
        String value = "anyValue";

        String hashedValue = sut.hash(value);

        assertNotNull(hashedValue);
        assertNotEquals(value, hashedValue);
        assertTrue(hashedValue.startsWith("$2a$") || hashedValue.startsWith("$2b$"));
    }
}
