package com.matheusgondra.books.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenAPIConfig {
    @Bean
    OpenAPI openAPI() {
        var info = new Info()
                .title("Books API")
                .description("API for managing books, authors, and user authentication.")
                .version("1.0.0");

        return new OpenAPI()
                .info(info)
                .addTagsItem(createAuthTag())
                .addTagsItem(createAuthorTag())
                .schemaRequirement("jwt_auth", createSecurityScheme());
    }

    private Tag createAuthTag() {
        return new Tag()
                .name("Authentication")
                .description("Endpoints for user authentication and token management.");
    }

    private Tag createAuthorTag() {
        return new Tag()
                .name("Author")
                .description("Endpoints for managing authors.");
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .name("jwt_auth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
