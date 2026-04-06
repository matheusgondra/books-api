package com.matheusgondra.books.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @Email @NotBlank String email,
        @Size(min = 6) @NotBlank String password) {
}
