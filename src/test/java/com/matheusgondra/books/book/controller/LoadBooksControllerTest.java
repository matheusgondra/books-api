package com.matheusgondra.books.book.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.config.BaseIntegrationTest;
import com.matheusgondra.books.exception.response.ErrorResponse;
import com.matheusgondra.books.factory.AuthorFactory;
import com.matheusgondra.books.factory.BookFactory;
import com.matheusgondra.books.helper.dto.TestPagedModel;
import com.matheusgondra.books.helper.dto.TestPagedModel.PageMetadata;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

public class LoadBooksControllerTest extends BaseIntegrationTest {
    private final String path = "/api/book";
    private final Author author = AuthorFactory.createWithoutId();
    private final List<Book> books = List.of(
            BookFactory.create(author, "1234567890123"),
            BookFactory.create(author, "0987654321098"),
            BookFactory.create(author, "1122334455667"));

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        registerBooks();
    }

    @Test
    void shouldReturn200OnSuccess() {
        var response = RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when()
                .get(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract()
                .as(new TypeRef<TestPagedModel<BookDetails>>() {});

        PageMetadata page = response.page();
        assertThat(page.totalElements()).isEqualTo(3);
        assertThat(page.totalPages()).isEqualTo(2);
        assertThat(page.size()).isEqualTo(2);
        assertThat(page.number()).isEqualTo(0);
        assertThat(response.content()).hasSize(2).first().satisfies(this::assertBookDetails);
    }

    @Test
    void shouldReturnEmptyPageWhenNoBooks() {
        bookRepository.deleteAll();

        var response = RestAssured.given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<TestPagedModel<BookDetails>>() {});

        PageMetadata page = response.page();
        assertThat(page.totalElements()).isEqualTo(0);
        assertThat(page.totalPages()).isEqualTo(0);
        assertThat(page.size()).isEqualTo(2);
        assertThat(page.number()).isEqualTo(0);
        assertThat(response.content()).isEmpty();
    }

    @Test
    void shouldReturn401WhenNoAccessToken() {
        ErrorResponse error = RestAssured.given()
                .queryParam("page", 0)
                .queryParam("size", 2)
                .when()
                .get(path)
                .then()
                .statusCode(401)
                .extract()
                .as(ErrorResponse.class);

        assertThat(error.status()).isEqualTo(401);
        assertThat(error.message()).isEqualTo("Unauthorized");
    }

    private void registerBooks() {
        Author savedAuthor = authorRepository.save(author);

        books.forEach(book -> book.setAuthor(savedAuthor));

        bookRepository.saveAll(books);
    }

    private void assertBookDetails(BookDetails bookDetails) {
        assertThat(bookDetails.id()).isNotNull();
        assertThat(bookDetails.title()).isEqualTo(books.get(0).getTitle());
        assertThat(bookDetails.author()).isEqualTo(author.getName());
        assertThat(bookDetails.isbn()).isEqualTo(books.get(0).getIsbn());
        assertThat(bookDetails.pages()).isEqualTo(books.get(0).getPages());
        assertThat(bookDetails.createdAt()).isNotNull();
        assertThat(bookDetails.updatedAt()).isNotNull();
    }
}
