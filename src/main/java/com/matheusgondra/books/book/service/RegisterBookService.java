package com.matheusgondra.books.book.service;

import org.springframework.stereotype.Service;

import com.matheusgondra.books.author.exception.AuthorNotFoundException;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.register.RegisterBook;
import com.matheusgondra.books.book.usecase.register.RegisterBookData;
import com.matheusgondra.books.book.usecase.register.RegisterBookResult;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RegisterBookService implements RegisterBook {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public RegisterBookResult execute(RegisterBookData data) {
        Author author = this.authorRepository.findByName(data.author()).orElseThrow(AuthorNotFoundException::new);

        Book book = Book.builder()
                .title(data.title())
                .isbn(data.isbn())
                .pages(data.pages())
                .author(author)
                .build();
        this.bookRepository.save(book);

        return null;
    }
}
