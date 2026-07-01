package com.matheusgondra.books.author.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.author.dto.request.RegisterAuthorRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

public class RegisterAuthorControllerTest extends BaseIntegrationTest {
    private final RegisterAuthorRequestDTO dto = new RegisterAuthorRequestDTO("anyAuthor");
    private final String path = "/api/author/register";

    @Test
    void shouldReturn201OnSuccess() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo(dto.name()))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    void shouldReturn409IfAuthorAlreadyExists() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .post(path);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("message", equalTo("Author already exists"))
                .body("status", equalTo(409));
    }

    @Test
    void shouldReturn401WhenNoAuthenticated() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(401)
                .body("message", equalTo("Unauthorized"))
                .body("status", equalTo(401));
    }

    @Test
    void shouldReturn400WhenInvalidRequest() {
        RegisterAuthorRequestDTO invalidDTO = new RegisterAuthorRequestDTO("");

        List<Map<String, String>> errors = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidDTO)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(400)
                .body("message", equalTo("Validation failed"))
                .body("status", equalTo(400))
                .extract()
                .jsonPath()
                .getList("errors");

        assertThat(errors)
                .hasSize(1)
                .extracting("field", "message")
                .containsExactlyInAnyOrder(tuple("name", "must not be blank"));
    }
}
