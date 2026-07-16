package com.matheusgondra.books.book.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasXPath;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import com.matheusgondra.books.exception.response.ErrorResponse;
import com.matheusgondra.books.factory.BookFactory;
import com.matheusgondra.books.factory.UserFactory;
import com.matheusgondra.books.user.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

class LoadBookByIdControllerTest extends BaseIntegrationTest {
    private final String path = "/api/book/{id}";

    private UUID id;
    private Book book;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        registerBookTest();
    }

    @Test
    void shouldReturn200OnSuccess() {
        BookDetails response = RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .get(path, id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract()
                .as(BookDetails.class);

        assertThat(response)
                .returns(book.getId(), BookDetails::id)
                .returns(book.getTitle(), BookDetails::title)
                .returns(book.getIsbn(), BookDetails::isbn)
                .returns(book.getAuthor().getName(), BookDetails::author)
                .returns(book.getCreatedAt(), BookDetails::createdAt)
                .returns(book.getUpdatedAt(), BookDetails::updatedAt);
    }

    @Test
    void shouldReturn200WithXml() throws Exception {
        XmlPath response = RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(ContentType.XML)
                .when()
                .get(path, id)
                .then()
                .contentType(ContentType.XML)
                .statusCode(200)
                .body(hasXPath("/details"))
                .extract()
                .xmlPath();

        assertThat(response.getUUID("details.id")).isEqualTo(book.getId());
        assertThat(response.getString("details.title")).isEqualTo(book.getTitle());
        assertThat(response.getString("details.isbn")).isEqualTo(book.getIsbn());
        assertThat(response.getInt("details.pages")).isEqualTo(book.getPages());
        assertThat(response.getString("details.author"))
                .isEqualTo(book.getAuthor().getName());
        assertThat(response.getString("details.createdAt")).isNotBlank();
        assertThat(response.getString("details.updatedAt")).isNotBlank();
    }

    @Test
    void shouldReturn401WhenNoAccessToken() {
        ErrorResponse response = RestAssured.given()
                .when()
                .get(path, id)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(401)
                .extract()
                .as(ErrorResponse.class);

        assertThat(response).returns(401, ErrorResponse::status).returns("Unauthorized", ErrorResponse::message);
    }

    @Test
    void shouldReturn404WhenBookNoExists() {
        ErrorResponse response = RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .get(path, UUID.randomUUID())
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .extract()
                .as(ErrorResponse.class);

        assertThat(response).returns(404, ErrorResponse::status).returns("Book not found", ErrorResponse::message);
    }

    private void registerBookTest() {
        Author savedAuthor = authorRepository.save(new Author("authorTest"));
        var savedOwner = userRepository.save(UserFactory.create());

        Book bookToSave = BookFactory.create(savedAuthor);
        bookToSave.setOwner(savedOwner);

        Book createdBook = bookRepository.save(bookToSave);

        book = bookRepository.findById(createdBook.getId()).orElseThrow();

        id = book.getId();
    }
}
