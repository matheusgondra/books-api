package com.matheusgondra.books.author.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonRootName("response")
public record RegisterAuthorResponseDTO(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {}
