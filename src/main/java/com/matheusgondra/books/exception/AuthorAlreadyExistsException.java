package com.matheusgondra.books.exception;

public class AuthorAlreadyExistsException extends RuntimeException {
    public AuthorAlreadyExistsException() {
        super("Author already exists");
    }
}