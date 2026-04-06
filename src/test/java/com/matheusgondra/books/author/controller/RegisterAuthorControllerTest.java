package com.matheusgondra.books.author.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.matheusgondra.books.auth.dto.request.SignupRequestDTO;
import com.matheusgondra.books.auth.dto.request.LoginRequestDTO;
import com.matheusgondra.books.author.dto.request.RegisterAuthorRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import tools.jackson.databind.ObjectMapper;

public class RegisterAuthorControllerTest extends BaseIntegrationTest {
    private final RegisterAuthorRequestDTO dto = new RegisterAuthorRequestDTO("anyAuthor");

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn201OnSuccess() {
        var signupDTO = new SignupRequestDTO("john", "doe", "john@mail.com", "Password@123");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(signupDTO))
                .when()
                .post("/api/signup")
                .then()
                .statusCode(201);

        var loginDTO = new LoginRequestDTO(signupDTO.email(), signupDTO.password());
        String loginResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(loginDTO))
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        String accessToken = objectMapper.readTree(loginResponse).get("access_token").asString();

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
