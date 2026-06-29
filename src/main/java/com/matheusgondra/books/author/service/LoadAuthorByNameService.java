package com.matheusgondra.books.author.service;

import com.matheusgondra.books.author.exception.AuthorNotFoundException;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.author.usecase.load.author.LoadAuthorByName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoadAuthorByNameService implements LoadAuthorByName {
    private final AuthorRepository repository;

    @Override
    public Author execute(String name) {
        return this.repository.findByName(name).orElseThrow(AuthorNotFoundException::new);
    }
}
