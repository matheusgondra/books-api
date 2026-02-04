package com.matheusgondra.books.auth.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.books.auth.usecase.register.user.RegisterUserData;
import com.matheusgondra.books.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class SignupServiceTest {
    @InjectMocks
    private SignupService sut;

    @Mock
    private UserRepository repository;

    private final RegisterUserData registerUserData = new RegisterUserData(
            "anyFirstName",
            "anyLastName",
            "any.email@example.com",
            "anyPassword@123");

    @Test
    void shouldCallFindByEmailMethod() {
        when(repository.findByEmail(registerUserData.email())).thenReturn(Optional.empty());

        sut.execute(registerUserData);

        verify(repository, times(1)).findByEmail(registerUserData.email());
    }
}
