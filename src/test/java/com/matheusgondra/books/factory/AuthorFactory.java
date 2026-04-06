package com.matheusgondra.books.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import com.matheusgondra.books.author.model.Author;

public class AuthorFactory {
    public static Author create() {
        return new Author(UUID.randomUUID(), "anyName", LocalDateTime.now(), LocalDateTime.now());
    }
}
