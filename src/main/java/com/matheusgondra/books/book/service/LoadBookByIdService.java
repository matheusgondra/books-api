package com.matheusgondra.books.book.service;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.load.LoadBookById;
import com.matheusgondra.books.exception.BookNotFoundException;
import com.matheusgondra.books.user.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoadBookByIdService implements LoadBookById {
    private final BookRepository repository;

    @Override
    public BookDetails execute(User owner, UUID id) {
        Book book = this.repository.findWithAuthorByIdAndOwner(id, owner).orElseThrow(BookNotFoundException::new);

        return new BookDetails(book);
    }
}
