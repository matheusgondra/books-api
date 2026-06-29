package com.matheusgondra.books.exception;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException() {
        super("A book with the same ISBN already exists.");
    }
}
