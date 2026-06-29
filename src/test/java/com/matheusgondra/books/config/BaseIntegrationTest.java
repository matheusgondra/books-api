package com.matheusgondra.books.config;

import com.matheusgondra.books.auth.helper.AuthHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {
    protected String accessToken;

    static final PostgreSQLContainer<?> postgres;

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthHelper authHelper;

    static {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.13-alpine3.23"));
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUpRestAssured() {
        RestAssured.port = port;

        accessToken = authHelper.getAccessToken();
    }

    @AfterEach
    void cleanDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE users, authors, books CASCADE");
    }
}
