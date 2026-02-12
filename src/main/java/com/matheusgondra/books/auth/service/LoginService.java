package com.matheusgondra.books.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.matheusgondra.books.auth.usecase.login.Login;
import com.matheusgondra.books.auth.usecase.login.LoginData;
import com.matheusgondra.books.auth.usecase.login.LoginResponse;
import com.matheusgondra.books.user.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService implements Login {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public LoginResponse execute(LoginData data) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(data.email(),
                data.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();
        tokenService.generateToken(user.getId().toString());

        return null;
    }
}
