package com.matheusgondra.books.book.service;

import com.matheusgondra.books.author.exception.AuthorNotFoundException;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;
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
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public BookDetails execute(UUID id, UpdateBookData data) {
        Book book = this.bookRepository.findWithAuthorById(id).orElseThrow(BookNotFoundException::new);

        if (data.author() != null && !data.author().equals(book.getAuthor().getName())) {
            this.authorRepository.findByName(data.author()).orElseThrow(AuthorNotFoundException::new);
        }

        return null;
    }
}
