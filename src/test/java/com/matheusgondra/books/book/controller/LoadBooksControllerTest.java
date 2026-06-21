package com.matheusgondra.books.book.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.auth.helper.AuthHelper;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import com.matheusgondra.books.factory.AuthorFactory;
import com.matheusgondra.books.factory.BookFactory;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoadBooksControllerTest extends BaseIntegrationTest {
    private final String path = "/api/book";
    private final Author author = AuthorFactory.createWithoutId();
    private final List<Book> books = List.of(
            BookFactory.create(author, "1234567890123"),
            BookFactory.create(author, "0987654321098"),
            BookFactory.create(author, "1122334455667"));

    private String accessToken;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthHelper authHelper;

    @BeforeEach
    void setUp() {
        accessToken = authHelper.getAccessToken();

        registerBooks();
    }

    @Test
    void shouldReturn200OnSuccess() {
        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .body("totalElements", equalTo(3))
                .body("totalPages", equalTo(2))
                .body("size", equalTo(2))
                .body("content.size()", equalTo(2))
                .body("content[0].id", notNullValue())
                .body("content[0].title", equalTo("anyTitle"))
                .body("content[0].isbn", equalTo("1234567890123"))
                .body("content[0].pages", equalTo(120))
                .body("content[0].author", equalTo("anyName"))
                .body("content[0].createdAt", notNullValue())
                .body("content[0].updatedAt", notNullValue());
    }

    @Test
    void shouldReturnEmptyPageWhenNoBooks() {
        bookRepository.deleteAll();

        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .body("totalElements", equalTo(0))
                .body("totalPages", equalTo(0))
                .body("size", equalTo(2))
                .body("content", equalTo(List.of()));
    }

    @Test
    void shouldReturn401WhenNoAccessToken() {
        RestAssured.given()
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when()
                .get(path)
                .then()
                .statusCode(401)
                .body("message", equalTo("Unauthorized"))
                .body("status", equalTo(401));
    }

    private void registerBooks() {
        Author savedAuthor = authorRepository.save(author);

        books.forEach(book -> book.setAuthor(savedAuthor));

        bookRepository.saveAll(books);
    }
}
