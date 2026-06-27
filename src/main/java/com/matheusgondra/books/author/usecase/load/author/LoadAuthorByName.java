package com.matheusgondra.books.author.usecase.load.author;

import com.matheusgondra.books.author.model.Author;

public interface LoadAuthorByName {
    Author execute(String name);
}
