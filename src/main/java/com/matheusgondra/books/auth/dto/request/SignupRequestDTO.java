package com.matheusgondra.books.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequestDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @Size(min = 6) @NotBlank String password) {

}
