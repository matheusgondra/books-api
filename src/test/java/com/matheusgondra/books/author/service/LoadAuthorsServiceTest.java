package com.matheusgondra.books.author.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.matheusgondra.books.author.repository.AuthorRepository;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class LoadAuthorsServiceTest {
    @InjectMocks
    private LoadAuthorsService sut;

    @Mock
    private AuthorRepository repository;

    private final Pageable pageable = Pageable.unpaged();

    @Test
    void shouldCallFindAllOnRepository() {
        sut.execute(pageable);

        verify(repository).findAll(pageable);
    }
}
