package com.matheusgondra.books.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonRootName("response")
public record SignupResponseDTO(
        UUID id, String firstName, String lastName, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {}
