package com.matheusgondra.books.author.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthorData;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthorResponse;
import com.matheusgondra.books.exception.AuthorAlreadyExistsException;
import com.matheusgondra.books.factory.AuthorFactory;

@ExtendWith(MockitoExtension.class)
public class RegisterAuthorServiceTest {
    private final RegisterAuthorData dataMock = new RegisterAuthorData("anyName");

    @InjectMocks
    private RegisterAuthorService sut;

    @Mock
    private AuthorRepository repository;

    @BeforeEach
    void setup() {
        lenient().when(repository.findByName(dataMock.name())).thenReturn(Optional.empty());
        lenient().when(repository.save(any(Author.class))).thenReturn(AuthorFactory.create());
    }

    @Test
    void shouldCallSaveMethodOnRepository() {
        sut.execute(dataMock);

        verify(repository, times(1)).save(any(Author.class));
    }

    @Test
    void shouldCallFindByNameMethodOnRepository() {
        sut.execute(dataMock);

        verify(repository, times(1)).findByName(dataMock.name());
    }

    @Test
    void shouldThrowAuthorAlreadyExistsIfAuthorAlreadyExists() {
        Author authorMock = AuthorFactory.create();

        when(repository.findByName(dataMock.name())).thenReturn(Optional.of(authorMock));

        assertThrows(AuthorAlreadyExistsException.class, () -> sut.execute(dataMock));
    }

    @Test
    void shouldThrowIfAuthorRepositoryThrows() {
        when(repository.save(any(Author.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> sut.execute(dataMock));
    }

    @Test
    void shouldReturnRegisterAuthorResponse() {
        RegisterAuthorResponse response = sut.execute(dataMock);

        assertEquals(dataMock.name(), response.name());
        assertNotNull(response.id());
        assertNotNull(response.createdAt());
        assertNotNull(response.updatedAt());
    }
}
