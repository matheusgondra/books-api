package com.matheusgondra.books.factory;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.user.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class BookFactory {
    public static Book create() {
        return Book.builder()
                .owner(new User())
                .title("anyTitle")
                .isbn("1234567890123")
                .pages(120)
                .build();
    }

    public static Book create(Author author) {
        Book book = create();
        book.setAuthor(author);
        return book;
    }

    public static Book create(Author author, String isbn) {
        Book book = create(author);
        book.setIsbn(isbn);
        return book;
    }

    public static Book createWithAuthor() {
        Author author = AuthorFactory.create();

        return create(author);
    }

    public static Page<Book> createPage() {
        List<Book> books = List.of(createWithAuthor(), createWithAuthor(), createWithAuthor());

        return new PageImpl<>(books);
    }

    public static Page<BookDetails> createDetailsPage() {
        return createPage().map(BookDetails::new);
    }
}
