package com.matheusgondra.books.auth.helper;

import com.matheusgondra.books.auth.dto.request.LoginRequestDTO;
import com.matheusgondra.books.auth.dto.request.SignupRequestDTO;
import com.matheusgondra.books.user.model.User;
import com.matheusgondra.books.user.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class AuthHelper {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public AuthSession getLoggedUser() {
        SignupRequestDTO signupDTO = createSignupRequest();
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

        String accessToken =
                objectMapper.readTree(loginResponse).get("accessToken").asString();

        User loggedUser = userRepository.findByEmail(signupDTO.email()).orElseThrow();
        return new AuthSession(loggedUser, accessToken);
    }

    private SignupRequestDTO createSignupRequest() {
        final String FIRST_NAME = "John";
        final String LAST_NAME = "Doe";
        final String EMAIL = "john.doe@gmail.com";
        final String PASSWORD = "Password@123";
        return new SignupRequestDTO(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD);
    }

    public record AuthSession(User user, String accessToken) {}
}
