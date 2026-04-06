package com.matheusgondra.books.author.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.matheusgondra.books.auth.helper.AuthHelper;
import com.matheusgondra.books.author.dto.request.RegisterAuthorRequestDTO;
import com.matheusgondra.books.config.BaseIntegrationTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import tools.jackson.databind.ObjectMapper;

public class RegisterAuthorControllerTest extends BaseIntegrationTest {
	private final RegisterAuthorRequestDTO dto = new RegisterAuthorRequestDTO("anyAuthor");

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthHelper authHelper;

	@Test
	void shouldReturn201OnSuccess() {
		String accessToken = authHelper.getAccessToken();

		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(objectMapper.writeValueAsString(dto))
				.header("Authorization", "Bearer " + accessToken)
				.when()
				.post("/api/author/register")
				.then()
				.statusCode(201);

	}
}
