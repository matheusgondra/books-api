package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import com.matheusgondra.books.book.dto.BookDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.exception.BookNotFoundException;
import com.matheusgondra.books.factory.BookFactory;

@ExtendWith(MockitoExtension.class)
public class LoadBookByIdServiceTest {
    private final UUID id = UUID.randomUUID();

    @InjectMocks
    private LoadBookByIdService sut;

    @Mock
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        when(repository.findWithAuthorById(id)).thenReturn(Optional.of(BookFactory.createWithAuthor()));
    }

    @Test
    void shouldCallFindByIdWithAuthorOnRepository() {
        sut.execute(id);

        verify(repository).findWithAuthorById(id);
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBookDoesNotExist() {
        when(repository.findWithAuthorById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> sut.execute(id));
    }

    @Test
    void shouldReturnBookDetailsOnSuccess() {
        BookDetails result = sut.execute(id);

        BookDetails expected = new BookDetails(BookFactory.createWithAuthor());

        assertEquals(expected, result);
    }
}
