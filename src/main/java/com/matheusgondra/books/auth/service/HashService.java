package com.matheusgondra.books.auth.service;

import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class HashService {
    public String hash(String value) {
        return BCrypt.withDefaults().hashToString(12, value.toCharArray());
    }
}
