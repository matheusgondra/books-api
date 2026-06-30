package com.matheusgondra.books.author.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

class LoadAuthorsControllerTest extends BaseIntegrationTest {
    private final String path = "/api/author";

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        registerAuthorsTest();
    }

    @Test
    void shouldReturn200OnSuccess() {
        RestAssured.given()
                .queryParam("page", 0)
                .queryParam("size", 2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .get(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("content", notNullValue())
                .body("content.size()", equalTo(2))
                .body("totalElements", equalTo(3))
                .body("totalPages", equalTo(2))
                .body("number", equalTo(0))
                .body("size", equalTo(2));
    }

    @Test
    void shouldReturn200WithEmptyContentWhenNoAuthors() {
        authorRepository.deleteAll();

        RestAssured.given()
                .queryParam("page", 0)
                .queryParam("size", 2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .get(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("content", notNullValue())
                .body("content.size()", equalTo(0))
                .body("totalElements", equalTo(0))
                .body("totalPages", equalTo(0))
                .body("number", equalTo(0))
                .body("size", equalTo(2));
    }

    private void registerAuthorsTest() {
        for (int i = 0; i < 3; i++) {
            registerAuthorTest();
        }
    }

    private void registerAuthorTest() {
        Author authorTest = new Author("anyName-" + System.currentTimeMillis());
        authorRepository.save(authorTest);
    }
}
