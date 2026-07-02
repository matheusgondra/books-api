package com.matheusgondra.books.book.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.matheusgondra.books.book.usecase.register.RegisterBookResult;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonRootName("response")
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
