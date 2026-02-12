package com.matheusgondra.books.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.matheusgondra.books.auth.usecase.login.Login;
import com.matheusgondra.books.auth.usecase.login.LoginData;
import com.matheusgondra.books.auth.usecase.login.LoginResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService implements Login {
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse execute(LoginData data) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(data.email(),
                data.password());
        authenticationManager.authenticate(authenticationToken);
        return null;
    }
}
