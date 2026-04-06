package com.matheusgondra.books.author.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterAuthorRequestDTO(@NotBlank String name) {

}
