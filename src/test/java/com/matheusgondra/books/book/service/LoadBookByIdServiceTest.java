package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.exception.BookNotFoundException;
import com.matheusgondra.books.factory.BookFactory;
import com.matheusgondra.books.factory.UserFactory;
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
public class LoadBookByIdServiceTest {
    private final UUID id = UUID.randomUUID();
    private final User owner = UserFactory.create();

    @InjectMocks
    private LoadBookByIdService sut;

    @Mock
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        when(repository.findWithAuthorByIdAndOwner(id, owner)).thenReturn(Optional.of(BookFactory.createWithAuthor()));
    }

    @Test
    void shouldCallFindByIdWithAuthorOnRepository() {
        sut.execute(owner, id);

        verify(repository).findWithAuthorByIdAndOwner(id, owner);
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBookDoesNotExist() {
        when(repository.findWithAuthorByIdAndOwner(id, owner)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> sut.execute(owner, id));
    }

    @Test
    void shouldReturnBookDetailsOnSuccess() {
        BookDetails result = sut.execute(owner, id);

        BookDetails expected = new BookDetails(BookFactory.createWithAuthor());

        assertEquals(expected, result);
    }
}
