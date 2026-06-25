package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.update.UpdateBookData;
import com.matheusgondra.books.exception.BookNotFoundException;
import com.matheusgondra.books.factory.BookFactory;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateBookServiceTest {
    private final UUID id = UUID.randomUUID();
    private final UpdateBookData data = new UpdateBookData("anyTitle", "anyAuthor", "anyIsbn", 120);

    @InjectMocks
    private UpdateBookService sut;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        Book bookMock = BookFactory.createWithAuthor();
        when(bookRepository.findWithAuthorById(id)).thenReturn(Optional.of(bookMock));
    }

    @Test
    void shouldCallFindWithAuthorByIdOnBookRepository() {
        sut.execute(id, data);

        verify(bookRepository).findWithAuthorById(id);
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBookIsNotFound() {
        when(bookRepository.findWithAuthorById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> sut.execute(id, data));
    }
}
