package com.matheusgondra.books.author.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.matheusgondra.books.author.dto.request.RegisterAuthorRequestDTO;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.ObjectMapper;

class LoadAuthorByNameControllerTest extends BaseIntegrationTest {
    private final String authorName = "anyAuthor-" + UUID.randomUUID().toString();
    private final RegisterAuthorRequestDTO dto = new RegisterAuthorRequestDTO(authorName);

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn200OnSuccess() {
        registerAuthorTest();

        String response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/author/loadByName/" + authorName)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        Author authorResponse = objectMapper.readValue(response, Author.class);

        assertEquals(authorName, authorResponse.getName());
    }

    private void registerAuthorTest() {

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
