package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import com.matheusgondra.books.book.repository.BookRepository;
import java.util.UUID;
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

    @Test
    void shouldCallDeleteByIdOnRepository() {
        sut.execute(id);

        verify(repository).deleteById(id);
    }
}
