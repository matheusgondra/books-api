package com.matheusgondra.books.auth.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SignupResponseDTO(
                UUID id,
                String firstName,
                String lastName,
                String email,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}