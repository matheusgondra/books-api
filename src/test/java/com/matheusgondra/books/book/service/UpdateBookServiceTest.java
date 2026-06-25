package com.matheusgondra.books.book.service;

import static org.mockito.Mockito.verify;

import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.update.UpdateBookData;
import java.util.UUID;
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

    @Test
    void shouldCallFindWithAuthorByIdOnBookRepository() {
        sut.execute(id, data);

        verify(bookRepository).findWithAuthorById(id);
    }
}
