package com.matheusgondra.books.author.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException() {
        super("Author not found");
    }
}
