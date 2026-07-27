package com.matheusgondra.books.book.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.factory.BookFactory;
import com.matheusgondra.books.user.model.User;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class LoadBooksServiceTest {
    private final User owner = User.builder().id(UUID.randomUUID()).build();
    private final Pageable pageable = PageRequest.of(0, 5);

    @InjectMocks
    private LoadBooksService sut;

    @Mock
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        Page<Book> page = BookFactory.createPage();

        when(repository.findByOwner(owner, pageable)).thenReturn(page);
    }

    @Test
    void shouldCallFindByOwnerOnRepository() {
        sut.execute(owner, pageable);

        verify(repository).findByOwner(owner, pageable);
    }

    @Test
    void shouldReturnPageOfBookDetailsOnSuccess() {
        Page<BookDetails> result = sut.execute(owner, pageable);

        var expected = BookFactory.createDetailsPage();

        assertEquals(expected, result);
    }
}
