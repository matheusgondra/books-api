package com.matheusgondra.books.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.matheusgondra.books.auth.dto.request.SignupRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;
import com.matheusgondra.books.user.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import tools.jackson.dataformat.xml.XmlMapper;

public class SignupControllerTest extends BaseIntegrationTest {
    private final String path = "/api/signup";

    @Autowired
    private UserRepository userRepository;

    private final SignupRequestDTO dto = new SignupRequestDTO("John", "Doe", "john.doe@email.com", "Password@123");

    @BeforeEach
    void setup() {
        this.userRepository.deleteAll();
    }

    @Test
    void shouldReturn201OnSuccess() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(path)
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("john.doe@email.com"))
                .body("createdAt", notNullValue())
                .body("updatedAt", notNullValue());
    }

    @Test
    void shouldReturn200WithXml() {
        XmlMapper xmlMapper = new XmlMapper();

        RestAssured.given()
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .body(xmlMapper.writeValueAsString(dto))
                .when()
                .post(path)
                .then()
                .statusCode(201)
                .contentType(ContentType.XML)
                .body("SignupResponseDTO.id", notNullValue())
                .body("SignupResponseDTO.firstName", equalTo("John"))
                .body("SignupResponseDTO.lastName", equalTo("Doe"))
                .body("SignupResponseDTO.email", equalTo("john.doe@email.com"))
                .body("SignupResponseDTO.createdAt", notNullValue())
                .body("SignupResponseDTO.updatedAt", notNullValue());
    }

    @Test
    void shouldReturn400OnInvalidRequest() {
        SignupRequestDTO invalidDTO = new SignupRequestDTO("", "Doe", "", "pwd");

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
                        tuple("firstName", "must not be blank"),
                        tuple("email", "must not be blank"),
                        tuple("password", "size must be between 6 and 2147483647"));
    }

    @Test
    void shouldReturn409WhenUserAlreadyExists() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(path)
                .then()
                .statusCode(201);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post(path)
                .then()
                .statusCode(409)
                .body("status", equalTo(409))
                .body("message", equalTo("User already exists"));
    }
}
