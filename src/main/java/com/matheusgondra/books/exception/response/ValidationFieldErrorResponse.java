package com.matheusgondra.books.exception.response;

import org.springframework.validation.FieldError;

public record ValidationFieldErrorResponse(String field, String message) {
    public ValidationFieldErrorResponse(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
