package com.matheusgondra.books.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
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
import com.matheusgondra.books.exception.InvalidCredentialsException;
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

        lenient().when(authMock.getPrincipal()).thenReturn(user);
        lenient().when(user.getId()).thenReturn(UUID.randomUUID());
        lenient().when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);
        lenient().when(tokenService.generateToken(anyString())).thenReturn("anyToken");
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

    @Test
    void shouldReturnToken() {
        var response = sut.execute(data);

        assertEquals(response.token(), "anyToken");
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenAuthenticationFails() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(RuntimeException.class);

        assertThrows(InvalidCredentialsException.class, () -> sut.execute(data));
    }
}
