package com.matheusgondra.books.book.service;

import com.matheusgondra.books.author.exception.AuthorNotFoundException;
import com.matheusgondra.books.author.model.Author;
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
        Book book = this.bookRepository
                .findWithAuthorByIdAndOwner(id, data.owner())
                .orElseThrow(BookNotFoundException::new);

        this.updateBook(book, data);

        this.bookRepository.save(book);

        return new BookDetails(book);
    }

    private void updateBook(Book book, UpdateBookData data) {
        boolean isAuthorChanged =
                data.author() != null && !data.author().equals(book.getAuthor().getName());
        if (isAuthorChanged) {
            Author author = this.authorRepository.findByName(data.author()).orElseThrow(AuthorNotFoundException::new);
            book.setAuthor(author);
        }

        boolean isTitleChanged = data.title() != null && !data.title().equals(book.getTitle());
        if (isTitleChanged) {
            book.setTitle(data.title());
        }

        boolean isIsbnChanged = data.isbn() != null && !data.isbn().equals(book.getIsbn());
        if (isIsbnChanged) {
            book.setIsbn(data.isbn());
        }

        boolean isPagesChanged = data.pages() != null && !data.pages().equals(book.getPages());
        if (isPagesChanged) {
            book.setPages(data.pages());
        }
    }
}
