package com.matheusgondra.books.auth.usecase.register.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterUserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
