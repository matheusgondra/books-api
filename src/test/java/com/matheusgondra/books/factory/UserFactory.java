package com.matheusgondra.books.factory;

import com.matheusgondra.books.cryptography.service.HashService;
import com.matheusgondra.books.user.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFactory {
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String EMAIL = "john.doe@gmail.com";
    public static final String PASSWORD = "Password@123";

    public static User create() {
        HashService hashService = new HashService(new BCryptPasswordEncoder());
        String passwordHash = hashService.hash(PASSWORD);

        return User.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .password(passwordHash)
                .build();
    }
}
