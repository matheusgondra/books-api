package com.matheusgondra.books.book.service;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.update.UpdateBook;
import com.matheusgondra.books.book.usecase.update.UpdateBookData;
import com.matheusgondra.books.exception.BookNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateBookService implements UpdateBook {
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookDetails execute(UUID id, UpdateBookData data) {
        this.bookRepository.findWithAuthorById(id).orElseThrow(BookNotFoundException::new);

        return null;
    }
}
