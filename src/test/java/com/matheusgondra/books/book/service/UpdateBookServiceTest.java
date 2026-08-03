package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.matheusgondra.books.author.exception.AuthorNotFoundException;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.update.UpdateBookData;
import com.matheusgondra.books.exception.BookNotFoundException;
import com.matheusgondra.books.factory.BookFactory;
import com.matheusgondra.books.user.model.User;
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
    private final UpdateBookData data = new UpdateBookData(new User(), "anyTitle", "anyAuthor", "anyIsbn", 120);
    private final Book bookMock = BookFactory.createWithAuthor();
    private final Author authorMock = bookMock.getAuthor();

    @InjectMocks
    private UpdateBookService sut;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {

        when(bookRepository.findWithAuthorByIdAndOwner(id, data.owner())).thenReturn(Optional.of(bookMock));
        lenient().when(authorRepository.findByName(data.author())).thenReturn(Optional.of(authorMock));
    }

    @Test
    void shouldCallFindWithAuthorByIdOnBookRepository() {
        sut.execute(id, data);

        verify(bookRepository).findWithAuthorByIdAndOwner(id, data.owner());
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBookIsNotFound() {
        when(bookRepository.findWithAuthorByIdAndOwner(id, data.owner())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> sut.execute(id, data));
    }

    @Test
    void shouldCallFindByNameOnAuthorRepositoryWhenAuthorIsDifferent() {
        sut.execute(id, data);

        verify(authorRepository).findByName(data.author());
    }

    @Test
    void shouldThrowAuthorNotFoundExceptionWhenAuthorIsDifferentAndNotFound() {
        when(authorRepository.findByName(data.author())).thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class, () -> sut.execute(id, data));
    }

    @Test
    void shouldSaveUpdatedBook() {
        sut.execute(id, data);

        verify(bookRepository).save(bookMock);
    }

    @Test
    void shouldReturnUpdatedBook() {
        BookDetails result = sut.execute(id, data);

        assertEquals(bookMock.getId(), result.id());
        assertEquals(bookMock.getTitle(), result.title());
        assertEquals(bookMock.getAuthor().getName(), result.author());
        assertEquals(bookMock.getIsbn(), result.isbn());
        assertEquals(bookMock.getPages(), result.pages());
    }
}
