package com.matheusgondra.books.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.auth.dto.request.LoginRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;
import com.matheusgondra.books.factory.UserFactory;
import com.matheusgondra.books.user.model.User;
import com.matheusgondra.books.user.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import tools.jackson.dataformat.xml.XmlMapper;

public class LoginControllerTest extends BaseIntegrationTest {
    private final String emailMock = "john.doe@gmail.com";
    private final String passwordMock = "Password@123";
    private final LoginRequestDTO dto = new LoginRequestDTO(emailMock, passwordMock);
    private final String path = "/api/login";

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        User user = UserFactory.create();

        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldReturn200OnSuccess() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(path)
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Test
    void shouldReturn200WithXML() {
        XmlMapper xmlMapper = new XmlMapper();

        RestAssured.given()
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .body(xmlMapper.writeValueAsString(dto))
                .when()
                .post(path)
                .then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body("LoginResponseDTO.accessToken", notNullValue());
    }

    @Test
    void shouldReturn400OnInvalidRequest() {
        var invalidDTO = new LoginRequestDTO("", "");

        List<Map<String, String>> errors = RestAssured.given()
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                .contentType(ContentType.JSON)
                .body(invalidDTO)
                .when()
                .post(path)
                .then()
                .statusCode(400)
                .body("status", equalTo(400))
                .body("message", equalTo("Validation failed"))
                .extract()
                .jsonPath()
                .getList("errors");

        assertThat(errors)
                .hasSize(3)
                .extracting("field", "message")
                .containsExactlyInAnyOrder(
                        tuple("email", "must not be blank"),
                        tuple("password", "must not be blank"),
                        tuple("password", "size must be between 6 and 2147483647"));
    }

    @Test
    void shouldReturn401OnInvalidCredentials() {
        var invalidDTO = new LoginRequestDTO("nonexistent@gmail.com", "WrongPassword@123");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(invalidDTO)
                .when()
                .post(path)
                .then()
                .statusCode(401)
                .body("status", equalTo(401))
                .body("message", equalTo("Invalid credentials"));
    }
}
