package com.matheusgondra.books.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.load.LoadBooks;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoadBooksService implements LoadBooks {
    private final BookRepository repository;

    @Override
    public Page<BookDetails> execute(Pageable pageable) {
        this.repository.findAll(pageable);

        return null;
    }
}
