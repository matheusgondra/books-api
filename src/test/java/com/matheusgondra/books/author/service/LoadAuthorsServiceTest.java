package com.matheusgondra.books.author.service;

import com.matheusgondra.books.author.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void shouldThrowIfRepositoryThrow() {
        when(repository.findAll(pageable)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> sut.execute(pageable));
    }
}
