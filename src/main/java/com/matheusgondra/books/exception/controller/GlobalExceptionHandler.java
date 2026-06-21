package com.matheusgondra.books.exception.controller;

import java.util.List;

import com.matheusgondra.books.author.exception.AuthorAlreadyExistsException;
import com.matheusgondra.books.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.matheusgondra.books.exception.BookAlreadyExistsException;
import com.matheusgondra.books.exception.InvalidCredentialsException;
import com.matheusgondra.books.exception.UserAlreadyExistsException;
import com.matheusgondra.books.exception.response.ErrorResponse;
import com.matheusgondra.books.exception.response.ValidationErrorResponse;
import com.matheusgondra.books.exception.response.ValidationFieldErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            AuthorAlreadyExistsException.class,
            BookAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex) {
        ErrorResponse errorResponse = ErrorResponse.conflict(ex);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleBadRequest(MethodArgumentNotValidException ex) {
        List<ValidationFieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(ValidationFieldErrorResponse::new)
                .toList();

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(HttpStatus.BAD_REQUEST,
                "Validation failed", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(BookNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.notFound(ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(InvalidCredentialsException ex) {
        ErrorResponse errorResponse = ErrorResponse.unauthorized(ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.serverError();

        log.error("Exception: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
