package com.matheusgondra.books.author.dto;

import com.matheusgondra.books.author.model.Author;
import java.time.LocalDateTime;
import java.util.UUID;

public record AuthorDetails(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public AuthorDetails(Author author) {
        this(author.getId(), author.getName(), author.getCreatedAt(), author.getUpdatedAt());
    }
}
