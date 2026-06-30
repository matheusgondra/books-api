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

class LoadAuthorByNameControllerTest extends BaseIntegrationTest {
    private final String authorName = "anyAuthor";
    private final Author author = new Author(authorName);
    private final String path = "/api/author/loadByName/{name}";

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        registerAuthorTest();
    }

    @Test
    void shouldReturn200OnSuccess() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .get(path, authorName)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo(authorName))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    void shouldReturn200WithXml() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT, ContentType.XML)
                .when()
                .get(path, authorName)
                .then()
                .contentType(ContentType.XML)
                .statusCode(200)
                .body("AuthorDetails.id", notNullValue())
                .body("AuthorDetails.name", equalTo(authorName))
                .body("AuthorDetails.createdAt", notNullValue())
                .body("AuthorDetails.updatedAt", notNullValue());
    }

    @Test
    void shouldReturn404WhenAuthorNotFound() {
        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .get(path, "nonExistentAuthor")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("message", equalTo("Author not found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldReturn401WhenNotAuthorized() {
        RestAssured.given()
                .when()
                .get(path, authorName)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(401)
                .body("message", equalTo("Unauthorized"))
                .body("status", equalTo(401));
    }

    private void registerAuthorTest() {
        authorRepository.save(author);
    }
}
