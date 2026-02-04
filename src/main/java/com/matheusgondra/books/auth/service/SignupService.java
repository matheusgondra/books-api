package com.matheusgondra.books.auth.service;

import org.springframework.stereotype.Service;

import com.matheusgondra.books.auth.usecase.register.user.RegisterUser;
import com.matheusgondra.books.auth.usecase.register.user.RegisterUserData;
import com.matheusgondra.books.auth.usecase.register.user.RegisterUserResponse;
import com.matheusgondra.books.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignupService implements RegisterUser {
    private final UserRepository repository;

    @Override
    public RegisterUserResponse execute(RegisterUserData registerUserData) {
        this.repository.findByEmail(registerUserData.email());

        return null;
    }
}
