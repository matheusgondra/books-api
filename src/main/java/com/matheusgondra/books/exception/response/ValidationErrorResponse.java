package com.matheusgondra.books.exception.response;

import java.util.List;
import org.springframework.http.HttpStatus;

public record ValidationErrorResponse(int status, String message, List<ValidationFieldErrorResponse> errors) {
    public ValidationErrorResponse(HttpStatus status, String message, List<ValidationFieldErrorResponse> errors) {
        this(status.value(), message, errors);
    }
}
