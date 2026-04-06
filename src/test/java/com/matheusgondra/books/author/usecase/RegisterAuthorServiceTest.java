package com.matheusgondra.books.author.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthorData;

@ExtendWith(MockitoExtension.class)
public class RegisterAuthorServiceTest {
    private final RegisterAuthorData dataMock = new RegisterAuthorData("anyName");

    @InjectMocks
    private RegisterAuthorService sut;

    @Mock
    private AuthorRepository repository;

    @Test
    void shouldCallSaveMethodOnRepository() {
        sut.execute(dataMock);

        verify(repository, times(1)).save(any(Author.class));
    }
}
