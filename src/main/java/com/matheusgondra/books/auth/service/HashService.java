package com.matheusgondra.books.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashService {
    private final PasswordEncoder passwordEncoder;

    public String hash(String value) {
        return passwordEncoder.encode(value);
    }
}
