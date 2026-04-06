package com.matheusgondra.books.auth.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.matheusgondra.books.auth.dto.request.LoginRequestDTO;
import com.matheusgondra.books.auth.dto.response.LoginResponseDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;
import com.matheusgondra.books.factory.UserFactory;
import com.matheusgondra.books.user.model.User;
import com.matheusgondra.books.user.repository.UserRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import tools.jackson.databind.ObjectMapper;

public class LoginControllerTest extends BaseIntegrationTest {
    private final String emailMock = "john.doe@gmail.com";
    private final String passwordMock = "Password@123";
    private final LoginRequestDTO dto = new LoginRequestDTO(emailMock, passwordMock);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        User user = UserFactory.create();

        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldReturn200OnSuccess() {
        String response = createBaseRequest()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        LoginResponseDTO loginResponse = objectMapper.readValue(response, LoginResponseDTO.class);

        assertNotNull(loginResponse.accessToken());
    }

    @Test
    void shouldReturn400OnInvalidRequest() {
        var invalidDTO = new LoginRequestDTO("", "");

        createBaseRequest(invalidDTO)
                .then()
                .statusCode(400);

    }

    private Response createBaseRequest() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post("/api/login");
    }

    private Response createBaseRequest(LoginRequestDTO dto) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(dto))
                .when()
                .post("/api/login");
    }
}
