package com.matheusgondra.books.book.controller;

import static org.hamcrest.Matchers.equalTo;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.dto.request.UpdateBookRequestDTO;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

class UpdateBookControllerTest extends BaseIntegrationTest {
    private final String path = "/api/book/";
    private final UpdateBookRequestDTO dto = new UpdateBookRequestDTO("anyTitleUpdated", null, null, null);

    private UUID id;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        Author author = authorRepository.save(new Author("anyName"));
        Book book = Book.builder()
                .title("anyTitle")
                .isbn("1234567891234")
                .pages(120)
                .author(author)
                .build();
        bookRepository.save(book);

        id = book.getId();
    }

    @Test
    void shouldReturn200OnSuccess() {
        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .put(path + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.toString()))
                .body("title", equalTo(dto.title()))
                .body("isbn", equalTo("1234567891234"))
                .body("pages", equalTo(120))
                .body("author", equalTo("anyName"));
    }

    @Test
    void shouldReturn404WhenBookNotFound() {
        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .put(path + UUID.randomUUID())
                .then()
                .statusCode(404)
                .body("message", equalTo("Book not found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldReturn404WhenAuthorNotFound() {
        UpdateBookRequestDTO invalidDto = new UpdateBookRequestDTO(null, null, "invalidAuthor", null);

        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(invalidDto))
                .when()
                .put(path + id)
                .then()
                .statusCode(404)
                .body("message", equalTo("Author not found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldReturn401WhenNotAuthorized() {
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .put(path + id)
                .then()
                .statusCode(401)
                .body("message", equalTo("Unauthorized"))
                .body("status", equalTo(401));
    }

    @Test
    void shouldReturn400WhenInvalidRequest() {
        UpdateBookRequestDTO invalidDto = new UpdateBookRequestDTO(null, null, null, -1);

        RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsString(invalidDto))
                .when()
                .put(path + id)
                .then()
                .statusCode(400)
                .body("message", equalTo("Validation failed"))
                .body("errors.size()", equalTo(1))
                .body("errors[0].field", equalTo("pages"))
                .body("errors[0].message", equalTo("must be greater than or equal to 20"))
                .body("status", equalTo(400));
    }
}
