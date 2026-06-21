package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.auth.helper.AuthHelper;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import com.matheusgondra.books.factory.BookFactory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.UUID;


class LoadBookByIdControllerTest extends BaseIntegrationTest {
    private final String path = "/api/book/";

    private String accessToken;
    private UUID id;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        accessToken = authHelper.getAccessToken();
        registerBookTest();
    }

    @Test
    void shouldReturn200OnSuccess() {
        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(path + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.toString()))
                .body("title", equalTo("anyTitle"))
                .body("isbn", equalTo("1234567890123"))
                .body("pages", equalTo(120))
                .body("author", equalTo("authorTest"))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    private void registerBookTest() {
        Author savedAuthor = authorRepository.save(new Author("authorTest"));

        Book book = bookRepository.save(BookFactory.create(savedAuthor));

        id = book.getId();
    }
}