package com.matheusgondra.books.auth.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.matheusgondra.books.auth.usecase.login.LoginData;
import com.matheusgondra.books.user.model.User;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @InjectMocks
    private LoginService sut;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private User user;

    @Mock
    private Authentication authMock;

    private LoginData data;

    @BeforeEach
    void setUp() {
        data = new LoginData("any@email.com", "anyPassword");

        when(user.getId()).thenReturn(UUID.randomUUID());
        when(authMock.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authMock);
    }

    @Test
    void shouldCallAuthenticationManager() {
        sut.execute(data);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void shouldCallTokenService() {

        sut.execute(data);

        verify(tokenService).generateToken(user.getId().toString());
    }
}
