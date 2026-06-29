package com.matheusgondra.books.author.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.author.dto.request.RegisterAuthorRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;

class LoadAuthorsControllerTest extends BaseIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200OnSuccess() {
        registerAuthorsTest();

        RestAssured.given()
                .queryParam("page", 0)
                .queryParam("size", 2)
                .header("Authorization", "Bearer " + accessToken)
                .get("/api/author")
                .then()
                .statusCode(200)
                .body("content", notNullValue())
                .body("content.size()", equalTo(2))
                .body("totalElements", equalTo(3))
                .body("totalPages", equalTo(2))
                .body("number", equalTo(0))
                .body("size", equalTo(2));
    }

    private void registerAuthorsTest() {
        for (int i = 0; i < 3; i++) {
            registerAuthorTest();
        }
    }

    private void registerAuthorTest() {
        final String authorName = "anyAuthor-" + UUID.randomUUID().toString();
        final RegisterAuthorRequestDTO dto = new RegisterAuthorRequestDTO(authorName);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(dto))
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/api/author/register")
                .then()
                .statusCode(201);
    }
}
