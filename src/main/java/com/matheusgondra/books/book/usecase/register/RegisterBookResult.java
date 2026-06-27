package com.matheusgondra.books.book.usecase.register;

import com.matheusgondra.books.author.model.Author;
import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterBookResult(
        UUID id,
        String title,
        Author author,
        String isbn,
        Integer pages,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {}
