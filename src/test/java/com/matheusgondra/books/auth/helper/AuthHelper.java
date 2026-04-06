package com.matheusgondra.books.auth.helper;

import org.springframework.stereotype.Component;

import com.matheusgondra.books.auth.dto.request.LoginRequestDTO;
import com.matheusgondra.books.auth.dto.request.SignupRequestDTO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class AuthHelper {
    private final ObjectMapper objectMapper;

    public String getAccessToken() {
        var signupDTO = new SignupRequestDTO("john", "doe", "john@mail.com", "Password@123");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(signupDTO))
                .when()
                .post("/api/signup")
                .then()
                .statusCode(201);

        var loginDTO = new LoginRequestDTO(signupDTO.email(), signupDTO.password());
        String loginResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(loginDTO))
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        String accessToken = objectMapper.readTree(loginResponse)
                .get("access_token")
                .asString();
        return accessToken;
    }
}
