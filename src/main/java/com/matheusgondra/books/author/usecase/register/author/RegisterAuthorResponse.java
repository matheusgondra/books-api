package com.matheusgondra.books.author.usecase.register.author;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterAuthorResponse(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
