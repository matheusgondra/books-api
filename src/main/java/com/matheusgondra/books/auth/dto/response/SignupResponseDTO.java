package com.matheusgondra.books.auth.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record SignupResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}