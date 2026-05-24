package com.matheusgondra.books.author.service;

import com.matheusgondra.books.author.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoadAuthorByNameServiceTest {
    private final String mockAuthorName = "anyName";

    @InjectMocks
    private LoadAuthorByNameService sut;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void shouldCallFindByNameMethodOnRepository() {
        sut.execute(mockAuthorName);

        verify(authorRepository).findByName(mockAuthorName);
    }
}