package com.matheusgondra.books.factory;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.book.model.Book;

public class BookFactory {
    public static Book create() {
        return Book.builder()
                .title("anyTitle")
                .isbn("1234567890123")
                .pages(120)
                .build();
    }

    public static Book create(Author author) {
        return Book.builder()
                .title("anyTitle")
                .isbn("1234567890123")
                .pages(120)
                .author(author)
                .build();
    }
}
