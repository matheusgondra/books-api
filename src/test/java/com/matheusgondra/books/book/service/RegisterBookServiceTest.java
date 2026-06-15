package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.books.author.exception.AuthorNotFoundException;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.usecase.register.RegisterBookData;

@ExtendWith(MockitoExtension.class)
public class RegisterBookServiceTest {
    private final RegisterBookData data = new RegisterBookData(
            "Book title",
            "Author name",
            "1234567890123",
            120);

    @InjectMocks
    private RegisterBookService sut;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        lenient().when(authorRepository.findByName(data.author())).thenReturn(Optional.of(new Author(data.author())));
    }

    @Test
    void shouldCallFindByNameOnAuthorRepository() {
        sut.execute(data);

        verify(authorRepository).findByName(data.author());
    }

    @Test
    void shouldAuthorNotFoundExceptionWhenAuthorNotFound() {
        when(authorRepository.findByName(data.author())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> sut.execute(data));
    }
}
