package com.matheusgondra.books.auth.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.matheusgondra.books.auth.usecase.login.LoginData;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @InjectMocks
    private LoginService sut;

    @Mock
    private AuthenticationManager authenticationManager;

    private LoginData data;

    @BeforeEach
    void setUp() {
        data = new LoginData("any@email.com", "anyPassword");
    }

    @Test
    void shouldCallAuthenticationManager() {
        sut.execute(data);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
