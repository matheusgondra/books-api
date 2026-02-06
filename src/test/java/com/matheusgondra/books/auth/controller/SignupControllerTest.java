package com.matheusgondra.books.auth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.matheusgondra.books.auth.dto.request.SignupRequestDTO;
import com.matheusgondra.books.auth.dto.response.SignupResponseDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;
import com.matheusgondra.books.user.repository.UserRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import tools.jackson.databind.ObjectMapper;

public class SignupControllerTest extends BaseIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        this.userRepository.deleteAll();
    }

    @Test
    void shouldReturn201OnSuccess() throws JsonMappingException, JsonProcessingException {
        SignupRequestDTO dto = new SignupRequestDTO(
                "John",
                "Doe",
                "john.doe@email.com",
                "Password@123");

        String response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(this.objectMapper.writeValueAsString(dto))
                .when()
                .post("/api/signup")
                .then()
                .statusCode(201)
                .extract()
                .response()
                .asString();
        SignupResponseDTO signupResponse = this.objectMapper.readValue(response, SignupResponseDTO.class);

        assertNotNull(signupResponse.id());
        assertEquals(signupResponse.firstName(), "John");
        assertEquals(signupResponse.lastName(), "Doe");
        assertEquals(signupResponse.email(), "john.doe@email.com");
        assertNotNull(signupResponse.createdAt());
        assertNotNull(signupResponse.updatedAt());
    }
}
