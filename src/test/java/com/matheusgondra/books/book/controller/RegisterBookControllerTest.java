package com.matheusgondra.books.book.controller;

import static org.hamcrest.Matchers.equalTo;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorRepository.save(new Author("anyAuthor"));
    }

    @Test
    void shouldReturn201OnSuccess() {
        String accessToken = authHelper.getAccessToken();

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
}