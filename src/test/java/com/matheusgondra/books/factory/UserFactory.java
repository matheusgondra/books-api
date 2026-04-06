package com.matheusgondra.books.factory;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.matheusgondra.books.cryptography.service.HashService;
import com.matheusgondra.books.user.model.User;

public class UserFactory {
    public static User create() {
        HashService hashService = new HashService(new BCryptPasswordEncoder());
        String passwordHash = hashService.hash("Password@123");

        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .password(passwordHash)
                .build();
    }
}
