package com.matheusgondra.books.author.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.author.usecase.load.author.LoadAuthors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoadAuthorsService implements LoadAuthors {
    private final AuthorRepository repository;

    @Override
    public Page<Author> execute(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
