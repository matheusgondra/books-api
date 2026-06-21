package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.matheusgondra.books.author.exception.AuthorNotFoundException;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.register.RegisterBookData;
import com.matheusgondra.books.book.usecase.register.RegisterBookResult;
import com.matheusgondra.books.exception.BookAlreadyExistsException;
import com.matheusgondra.books.factory.AuthorFactory;
import com.matheusgondra.books.factory.BookFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterBookServiceTest {
    private final RegisterBookData data = new RegisterBookData("anyTitle", "anyName", "1234567890123", 120);
    private final Author authorMock = AuthorFactory.create();
    private final Book bookMock = BookFactory.create(authorMock);

    @InjectMocks
    private RegisterBookService sut;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        lenient().when(authorRepository.findByName(data.author())).thenReturn(Optional.of(authorMock));
        lenient().when(bookRepository.save(any(Book.class))).thenReturn(bookMock);
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

    @Test
    void shouldThrowBookAlreadyExistsOnDuplicateIsbn() {
        when(bookRepository.findByIsbn(data.isbn())).thenReturn(Optional.of(bookMock));

        assertThrows(BookAlreadyExistsException.class, () -> sut.execute(data));
    }

    @Test
    void shouldCallSaveOnBookRepository() {
        sut.execute(data);

        verify(bookRepository).save(bookMock);
    }

    @Test
    void shouldReturnRegisterBookResult() {
        RegisterBookResult result = sut.execute(data);

        assertEquals(bookMock.getTitle(), result.title());
        assertEquals(bookMock.getAuthor(), result.author());
        assertEquals(bookMock.getIsbn(), result.isbn());
        assertEquals(bookMock.getPages(), result.pages());
    }
}
