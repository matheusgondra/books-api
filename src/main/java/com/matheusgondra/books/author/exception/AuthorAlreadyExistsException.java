package com.matheusgondra.books.author.exception;

public class AuthorAlreadyExistsException extends RuntimeException {
    public AuthorAlreadyExistsException() {
        super("Author already exists");
    }
}