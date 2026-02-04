package com.matheusgondra.books.auth.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.books.auth.usecase.register.user.RegisterUserData;
import com.matheusgondra.books.exception.UserAlreadyExistsException;
import com.matheusgondra.books.user.model.User;
import com.matheusgondra.books.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class SignupServiceTest {
    @InjectMocks
    private SignupService sut;

    @Mock
    private UserRepository repository;

    @Mock
    private HashService hashService;

    private final RegisterUserData registerUserData = new RegisterUserData(
            "anyFirstName",
            "anyLastName",
            "any.email@example.com",
            "anyPassword@123");

    @BeforeEach
    void setup() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        lenient().when(hashService.hash(anyString())).thenReturn("anyHash");
    }

    @Test
    void shouldCallFindByEmailMethod() {
        sut.execute(registerUserData);

        verify(repository, times(1)).findByEmail(registerUserData.email());
    }

    @Test
    void shouldThrowExceptionIfUserRespositoryReturnAUser() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> {
            sut.execute(registerUserData);
        });
    }

    @Test
    void shouldCallHashService() {
        sut.execute(registerUserData);

        verify(hashService, times(1)).hash(registerUserData.password());
    }

    @Test
    void shouldCallSaveMethod() {
        sut.execute(registerUserData);

        User expectedUser = new User(
                registerUserData.firstName(),
                registerUserData.lastName(),
                registerUserData.email(),
                "anyHash");

        verify(repository, times(1)).save(expectedUser);
    }
}
