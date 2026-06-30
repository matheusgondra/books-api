package com.matheusgondra.books.author.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.author.dto.request.RegisterAuthorRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LoadAuthorByNameControllerTest extends BaseIntegrationTest {
    private final String authorName = "anyAuthor-" + UUID.randomUUID().toString();
    private final String path = "/api/author/loadByName/{name}";
    private final RegisterAuthorRequestDTO dto = new RegisterAuthorRequestDTO(authorName);

    @Test
    void shouldReturn200OnSuccess() {
        registerAuthorTest();

        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
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
    void shouldReturn404WhenAuthorNotFound() {
        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get(path, "nonExistentAuthor")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("message", equalTo("Author not found"))
                .body("status", equalTo(404));
    }

    private void registerAuthorTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/api/author/register")
                .then()
                .statusCode(201);
    }
}
