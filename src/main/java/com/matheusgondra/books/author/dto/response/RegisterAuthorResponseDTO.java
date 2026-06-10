package com.matheusgondra.books.author.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterAuthorResponseDTO(
                UUID id,
                String name,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
