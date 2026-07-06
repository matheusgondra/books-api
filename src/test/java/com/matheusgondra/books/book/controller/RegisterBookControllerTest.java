package com.matheusgondra.books.book.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.dto.request.RegisterBookRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import tools.jackson.dataformat.xml.XmlMapper;

public class RegisterBookControllerTest extends BaseIntegrationTest {
    private final RegisterBookRequestDTO dto = new RegisterBookRequestDTO("anyTitle", "anyAuthor", "123456789", 120);
    private final String path = "/api/book/register";

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorRepository.save(new Author("anyAuthor"));
    }

    @Test
    void shouldReturn201OnSuccess() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(dto)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
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
    void shouldReturn201WithXml() {
        var mapper = new XmlMapper();

        RestAssured.given()
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(mapper.writeValueAsString(dto))
                .when()
                .post(path)
                .then()
                .contentType(ContentType.XML)
                .statusCode(201)
                .body(hasXPath("/response"))
                .body("response.id", notNullValue())
                .body("response.title", equalTo(dto.title()))
                .body("response.author", equalTo(dto.author()))
                .body("response.isbn", equalTo(dto.isbn()))
                .body("response.pages", equalTo(dto.pages().toString()))
                .body("response.createdAt", notNullValue())
                .body("response.updatedAt", notNullValue());
    }

    @Test
    void shouldReturn404WhenAuthorNotExists() {
        authorRepository.deleteAll();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(dto)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("message", equalTo("Author not found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldReturn409OnDuplicateISBN() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(dto)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(201);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(dto)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(409)
                .body("status", equalTo(409))
                .body("message", equalTo("A book with the same ISBN already exists."));
    }

    @Test
    void shouldReturn401OnUnauthorized() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(401)
                .body("status", equalTo(401))
                .body("message", equalTo("Unauthorized"));
    }

    @Test
    void shouldReturn400OnInvalidRequest() {
        final RegisterBookRequestDTO invalidDto = new RegisterBookRequestDTO("anyTitle", "anyAuthor", "", 1);

        List<Map<String, String>> errors = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                .body(invalidDto)
                .when()
                .post(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(400)
                .body("status", equalTo(400))
                .body("message", equalTo("Validation failed"))
                .extract()
                .jsonPath()
                .getList("errors");

        assertThat(errors)
                .hasSize(2)
                .extracting("field", "message")
                .containsExactlyInAnyOrder(
                        tuple("isbn", "must not be blank"), tuple("pages", "must be greater than or equal to 10"));
    }
}
