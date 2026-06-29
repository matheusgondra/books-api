package com.matheusgondra.books.cryptography.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashService {
    private final PasswordEncoder passwordEncoder;

    public String hash(String value) {
        return passwordEncoder.encode(value);
    }
}
