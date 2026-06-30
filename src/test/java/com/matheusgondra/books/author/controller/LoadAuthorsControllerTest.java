package com.matheusgondra.books.author.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.UUID;
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
                .body("page.totalElements", equalTo(3))
                .body("page.totalPages", equalTo(2))
                .body("page.number", equalTo(0))
                .body("page.size", equalTo(2));
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
                .body("page.totalElements", equalTo(0))
                .body("page.totalPages", equalTo(0))
                .body("page.number", equalTo(0))
                .body("page.size", equalTo(2));
    }

    @Test
    void shouldReturn200WithXml() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT, ContentType.XML)
                .get(path)
                .then()
                .contentType(ContentType.XML)
                .statusCode(200)
                .body("PagedModel.content", notNullValue())
                .body("PagedModel.content.children().size()", equalTo(3))
                .body("PagedModel.page.totalElements", equalTo("3"))
                .body("PagedModel.page.totalPages", equalTo("1"))
                .body("PagedModel.page.number", equalTo("0"))
                .body("PagedModel.page.size", equalTo("20"));
    }

    @Test
    void shouldReturn401WhenNotAuthenticated() {
        RestAssured.given()
                .get(path)
                .then()
                .statusCode(401)
                .body("message", equalTo("Unauthorized"))
                .body("status", equalTo(401));
    }

    private void registerAuthorsTest() {
        Author authorOne = new Author("anyName-" + UUID.randomUUID());
        Author authorTwo = new Author("anyName-" + UUID.randomUUID());
        Author authorThree = new Author("anyName-" + UUID.randomUUID());
        authorRepository.saveAll(List.of(authorOne, authorTwo, authorThree));
    }
}
