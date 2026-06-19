package com.matheusgondra.books.factory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;

public class BookFactory {
    public static Book create() {
        return Book.builder()
                .title("anyTitle")
                .isbn("1234567890123")
                .pages(120)
                .build();
    }

    public static Book create(boolean withAuthor) {
        Author author = AuthorFactory.create();

        return create(author);
    }

    public static Book create(Author author) {
        return Book.builder()
                .title("anyTitle")
                .isbn("1234567890123")
                .pages(120)
                .author(author)
                .build();
    }

    public static Book create(Author author, String isbn) {
        return Book.builder()
                .title("anyTitle")
                .isbn(isbn)
                .pages(120)
                .author(author)
                .build();
    }

    public static Page<Book> createPage() {
        List<Book> books = List.of(create(true), create(true), create(true));

        return new PageImpl<>(books);
    }

    public static Page<BookDetails> createDetailsPage() {
        return createPage().map(BookDetails::new);
    }
}
