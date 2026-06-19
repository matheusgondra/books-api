package com.matheusgondra.books.book.service;

import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.books.book.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class LoadBookByIdServiceTest {
    private final UUID id = UUID.randomUUID();

    @InjectMocks
    private LoadBookByIdService sut;

    @Mock
    private BookRepository repository;

    @Test
    void shouldCallFindByIdOnRepository() {
        sut.execute(id);

        verify(repository).findById(id);
    }
}
