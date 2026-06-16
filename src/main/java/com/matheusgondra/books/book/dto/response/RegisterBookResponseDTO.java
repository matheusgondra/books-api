package com.matheusgondra.books.book.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.matheusgondra.books.book.usecase.register.RegisterBookResult;

public record RegisterBookResponseDTO(
        UUID id,
        String title,
        String author,
        String isbn,
        Integer pages,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public RegisterBookResponseDTO(RegisterBookResult result) {
        this(
                result.id(),
                result.title(),
                result.author().getName(),
                result.isbn(),
                result.pages(),
                result.createdAt(),
                result.updatedAt());
    }
}
