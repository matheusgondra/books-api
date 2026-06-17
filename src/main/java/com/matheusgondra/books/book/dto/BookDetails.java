package com.matheusgondra.books.book.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.matheusgondra.books.book.model.Book;

public record BookDetails(
        UUID id,
        String title,
        String author,
        String isbn,
        int pages,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public BookDetails(Book book) {
        this(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getIsbn(),
                book.getPages(),
                book.getCreatedAt(),
                book.getUpdatedAt());
    }
}
