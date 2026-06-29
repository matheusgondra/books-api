package com.matheusgondra.books.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Book not found");
    }
}
