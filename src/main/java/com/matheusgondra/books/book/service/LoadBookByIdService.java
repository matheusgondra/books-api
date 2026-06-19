package com.matheusgondra.books.book.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.load.LoadBookById;
import com.matheusgondra.books.exception.BookNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoadBookByIdService implements LoadBookById {
    private final BookRepository repository;

    @Override
    public BookDetails execute(UUID id) {
        this.repository.findById(id).orElseThrow(BookNotFoundException::new);

        return null;
    }
}
