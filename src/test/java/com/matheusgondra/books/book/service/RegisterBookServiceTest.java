package com.matheusgondra.books.book.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.usecase.register.RegisterBookData;

@ExtendWith(MockitoExtension.class)
public class RegisterBookServiceTest {
    private final RegisterBookData data = new RegisterBookData(
            "Book title",
            "Author name",
            "1234567890123",
            120);

    @InjectMocks
    private RegisterBookService sut;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void shouldCallFindByNameOnAuthorRepository() {
        sut.execute(data);

        verify(authorRepository).findByName(data.author());
    }
}
