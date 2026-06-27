package com.matheusgondra.books.exception.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int status, String message) {
    public ErrorResponse(HttpStatus status, String message) {
        this(status.value(), message);
    }

    public static ErrorResponse notFound(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    public static ErrorResponse unauthorized(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    public static ErrorResponse conflict(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    public static ErrorResponse serverError() {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }
}
