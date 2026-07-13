package com.matheusgondra.books.book.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.matheusgondra.books.book.model.Book;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonRootName("details")
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
