package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.exception.BookNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteBookServiceTest {
    @InjectMocks
    private DeleteBookService sut;

    @Mock
    private BookRepository repository;

    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        when(repository.findById(id)).thenReturn(Optional.of(new Book()));
    }

    @Test
    void shouldCallDeleteByIdOnRepository() {
        sut.execute(id);

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBookDoesNotExist() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> sut.execute(id));
    }
}
