package com.matheusgondra.books.book.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.matheusgondra.books.auth.helper.AuthHelper;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.dto.request.RegisterBookRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;

import io.restassured.RestAssured;
import tools.jackson.databind.ObjectMapper;

public class RegisterBookControllerTest extends BaseIntegrationTest {
    private final RegisterBookRequestDTO dto = new RegisterBookRequestDTO(
            "anyTitle",
            "anyAuthor",
            "123456789",
            120);
    private final String path = "/api/book/register";

    private String accessToken;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        accessToken = authHelper.getAccessToken();
        authorRepository.save(new Author("anyAuthor"));
    }

    @Test
    void shouldReturn201OnSuccess() {

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post(path)
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo(dto.title()))
                .body("author", equalTo(dto.author()))
                .body("isbn", equalTo(dto.isbn()))
                .body("pages", equalTo(dto.pages()))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    void shouldReturn404WhenAuthorNotExists() {
        authorRepository.deleteAll();

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post(path)
                .then()
                .statusCode(404)
                .body("message", equalTo("Author not found"));
    }

    @Test
    void shouldReturn409OnDuplicateISBN() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post(path)
                .then()
                .statusCode(201);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post(path)
                .then()
                .statusCode(409)
                .body("status", equalTo(409))
                .body("message", equalTo("A book with the same ISBN already exists."));
    }

    @Test
    void shouldReturn401OnUnauthorized() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post(path)
                .then()
                .statusCode(401);
    }

    @Test
    void shouldReturn400OnInvalidRequest() {
        final RegisterBookRequestDTO invalidDto = new RegisterBookRequestDTO(
                "anyTitle",
                "anyAuthor",
                "",
                1);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .body(objectMapper.writeValueAsString(invalidDto))
                .when()
                .post(path)
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("message", equalTo("Validation failed"))
                .body("errors", hasSize(2))
                .body("errors.field", containsInAnyOrder("isbn", "pages"))
                .body("errors.message", hasSize(2));
    }
}