package com.matheusgondra.books.author.usecase.load.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matheusgondra.books.author.model.Author;

public interface LoadAuthors {
    Page<Author> execute(Pageable pageable);
}
