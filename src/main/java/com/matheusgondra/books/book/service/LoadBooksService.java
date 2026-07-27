package com.matheusgondra.books.book.service;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.load.LoadBooks;
import com.matheusgondra.books.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoadBooksService implements LoadBooks {
    private final BookRepository repository;

    @Override
    public Page<BookDetails> execute(@AuthenticationPrincipal User owner, Pageable pageable) {
        return this.repository.findByOwner(owner, pageable).map(BookDetails::new);
    }
}
