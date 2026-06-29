package com.matheusgondra.books.factory;

import com.matheusgondra.books.author.model.Author;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class AuthorFactory {
    public static Author create() {
        LocalDateTime now = LocalDateTime.now();

        return Author.builder()
                .id(UUID.randomUUID())
                .name("anyName")
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public static Author createWithoutId() {
        LocalDateTime now = LocalDateTime.now();

        return Author.builder().name("anyName").createdAt(now).updatedAt(now).build();
    }

    public static Page<Author> createPage() {
        List<Author> authors = List.of(create(), create(), create());

        return new PageImpl<>(authors);
    }
}
