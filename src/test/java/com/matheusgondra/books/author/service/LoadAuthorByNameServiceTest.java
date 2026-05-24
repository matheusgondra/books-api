package com.matheusgondra.books.author.service;

import com.matheusgondra.books.author.exception.AuthorNotFoundException;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LoadAuthorByNameServiceTest {
    private final String mockAuthorName = "anyName";

    @InjectMocks
    private LoadAuthorByNameService sut;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        lenient().when(authorRepository.findByName(mockAuthorName)).thenReturn(Optional.of(new Author()));
    }

    @Test
    void shouldCallFindByNameMethodOnRepository() {
        sut.execute(mockAuthorName);

        verify(authorRepository).findByName(mockAuthorName);
    }

    @Test
    void shouldThrowIfAuthorNotExists() {
        when(authorRepository.findByName(mockAuthorName)).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> sut.execute(mockAuthorName));
    }
}