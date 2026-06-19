package com.matheusgondra.books.author.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.matheusgondra.books.author.model.Author;

public record AuthorDetails(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public AuthorDetails(Author author) {
        this(author.getId(), author.getName(), author.getCreatedAt(), author.getUpdatedAt());
    }
}
