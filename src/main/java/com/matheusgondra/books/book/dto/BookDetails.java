package com.matheusgondra.books.book.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookDetails(
        UUID id,
        String title,
        String author,
        String isbn,
        int pages,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
