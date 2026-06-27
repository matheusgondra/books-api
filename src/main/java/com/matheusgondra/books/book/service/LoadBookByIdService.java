package com.matheusgondra.books.book.service;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.load.LoadBookById;
import com.matheusgondra.books.exception.BookNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
