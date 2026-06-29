package com.matheusgondra.books.book.controller;

import static org.hamcrest.Matchers.equalTo;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public class DeleteBookControllerTest extends BaseIntegrationTest {
    private final String path = "/api/book/";

    private UUID id;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        Author author = authorRepository.save(new Author("anyName"));
        Book book = Book.builder()
                .title("anyTitle")
                .author(author)
                .isbn("1234567890123")
                .pages(120)
                .build();

        bookRepository.save(book);

        id = book.getId();
    }

    @Test
    void shouldReturn204OnSuccess() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .delete(path + id)
                .then()
                .statusCode(204);
    }

    @Test
    void shouldReturn404WhenBookNotFound() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .delete(path + UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("message", equalTo("Book not found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldReturn401WhenNotAuthorized() {
        RestAssured.given()
                .delete(path + UUID.randomUUID())
                .then()
                .statusCode(401)
                .body("message", equalTo("Unauthorized"))
                .body("status", equalTo(401));
    }
}
