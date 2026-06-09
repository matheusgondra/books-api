package com.matheusgondra.books.factory;

import com.matheusgondra.books.author.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AuthorFactory {
    public static Author create() {
        LocalDateTime now = LocalDateTime.now();

        return new Author(UUID.randomUUID(), "anyName", now, now);
    }

    public static Page<Author> createPage() {
        List<Author> authors = List.of(create(), create(), create());

        return new PageImpl<>(authors);
    }
}
