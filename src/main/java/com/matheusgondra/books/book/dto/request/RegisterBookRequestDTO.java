package com.matheusgondra.books.book.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterBookRequestDTO(
        @NotBlank String title, @NotBlank String author, @NotBlank String isbn, @Min(10) @NotNull Integer pages) {}
