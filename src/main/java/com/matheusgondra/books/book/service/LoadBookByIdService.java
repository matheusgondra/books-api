package com.matheusgondra.books.book.service;

import java.util.UUID;

import com.matheusgondra.books.book.model.Book;
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
        Book book = this.repository.findWithAuthorById(id).orElseThrow(BookNotFoundException::new);

        return new BookDetails(book);
    }
}
