package com.matheusgondra.books.book.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.dto.request.UpdateBookRequestDTO;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import tools.jackson.dataformat.xml.XmlMapper;

class UpdateBookControllerTest extends BaseIntegrationTest {
    private final String path = "/api/book/{id}";
    private final UpdateBookRequestDTO dto = new UpdateBookRequestDTO("anyTitleUpdated", null, null, null);

    private UUID id;

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
                .owner(loggedUser)
                .build();
        bookRepository.save(book);

        id = book.getId();
    }

    @Test
    void shouldReturn200OnSuccess() {
        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .put(path, id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("id", equalTo(id.toString()))
                .body("title", equalTo(dto.title()))
                .body("isbn", equalTo("1234567891234"))
                .body("pages", equalTo(120))
                .body("author", equalTo("anyName"));
    }

    @Test
    void shouldReturn200WithXml() {
        XmlMapper mapper = new XmlMapper();

        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .body(mapper.writeValueAsString(dto))
                .when()
                .put(path, id)
                .then()
                .contentType(ContentType.XML)
                .statusCode(200)
                .body(hasXPath("/details"))
                .body("details.id", equalTo(id.toString()))
                .body("details.title", equalTo(dto.title()))
                .body("details.isbn", equalTo("1234567891234"))
                .body("details.pages", equalTo("120"))
                .body("details.author", equalTo("anyName"));
    }

    @Test
    void shouldReturn404WhenBookNotFound() {
        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .put(path, UUID.randomUUID())
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("message", equalTo("Book not found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldReturn404WhenAuthorNotFound() {
        UpdateBookRequestDTO invalidDto = new UpdateBookRequestDTO(null, null, "invalidAuthor", null);

        RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(invalidDto)
                .when()
                .put(path, id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("message", equalTo("Author not found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldReturn401WhenNotAuthorized() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .put(path, id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(401)
                .body("message", equalTo("Unauthorized"))
                .body("status", equalTo(401));
    }

    @Test
    void shouldReturn400WhenInvalidRequest() {
        UpdateBookRequestDTO invalidDto = new UpdateBookRequestDTO(null, null, null, -1);

        List<Map<String, String>> errors = RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                .contentType(ContentType.JSON)
                .body(invalidDto)
                .when()
                .put(path, id)
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
                .containsExactlyInAnyOrder(tuple("pages", "must be greater than or equal to 20"));
    }
}
