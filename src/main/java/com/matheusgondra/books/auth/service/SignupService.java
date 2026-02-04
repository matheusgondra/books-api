package com.matheusgondra.books.auth.service;

import org.springframework.stereotype.Service;

import com.matheusgondra.books.auth.usecase.register.user.RegisterUser;
import com.matheusgondra.books.auth.usecase.register.user.RegisterUserData;
import com.matheusgondra.books.auth.usecase.register.user.RegisterUserResponse;
import com.matheusgondra.books.exception.UserAlreadyExistsException;
import com.matheusgondra.books.user.model.User;
import com.matheusgondra.books.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SignupService implements RegisterUser {
    private final UserRepository repository;
    private final HashService hashService;

    @Transactional
    @Override
    public RegisterUserResponse execute(RegisterUserData registerUserData) {
        this.repository.findByEmail(registerUserData.email()).ifPresent(user -> {
            throw new UserAlreadyExistsException();
        });

        String hashedPassword = this.hashService.hash(registerUserData.password());

        User newUser = new User(
                registerUserData.firstName(),
                registerUserData.lastName(),
                registerUserData.email(),
                hashedPassword);
        this.repository.save(newUser);

        return null;
    }
}
