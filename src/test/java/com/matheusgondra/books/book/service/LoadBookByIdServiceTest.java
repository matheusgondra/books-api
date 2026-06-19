package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

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
        when(repository.findById(id)).thenReturn(Optional.of(BookFactory.create()));
    }

    @Test
    void shouldCallFindByIdOnRepository() {
        sut.execute(id);

        verify(repository).findById(id);
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBookDoesNotExist() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> sut.execute(id));
    }
}
