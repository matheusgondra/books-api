package com.matheusgondra.books.author.service;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.factory.AuthorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private final Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        Page<Author> page = AuthorFactory.createPage();

        when(repository.findAll(pageable)).thenReturn(page);
    }

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

    @Test
    void shouldReturnPageOnSuccess() {
        Page<Author> result = sut.execute(pageable);

        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }
}
