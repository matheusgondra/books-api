package com.matheusgondra.books.book.controller;

import static org.hamcrest.Matchers.equalTo;

import com.matheusgondra.books.auth.helper.AuthHelper;
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
import org.springframework.http.MediaType;
import tools.jackson.databind.ObjectMapper;

class UpdateBookControllerTest extends BaseIntegrationTest {
    private final String path = "/api/book/";
    private final UpdateBookRequestDTO dto = new UpdateBookRequestDTO("anyTitleUpdated", null, null, null);

    private String accessToken;
    private UUID id;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        accessToken = authHelper.getAccessToken();

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
}
