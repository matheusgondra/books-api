package com.matheusgondra.books.book.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateBookRequestDTO(
        @Size(min = 5) String title,
        @Size(min = 13, max = 13) String isbn,
        @Size(min = 3) String author,
        @Min(20) Integer pages) {}
