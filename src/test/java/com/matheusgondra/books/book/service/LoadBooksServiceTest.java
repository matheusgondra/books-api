package com.matheusgondra.books.book.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.matheusgondra.books.book.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class LoadBooksServiceTest {
    private final Pageable pageable = PageRequest.of(0, 5);

    @InjectMocks
    private LoadBooksService sut;

    @Mock
    private BookRepository repository;

    @Test
    void shouldCallFindAllOnRepository() {
        sut.execute(pageable);

        verify(repository).findAll(pageable);
    }
}
