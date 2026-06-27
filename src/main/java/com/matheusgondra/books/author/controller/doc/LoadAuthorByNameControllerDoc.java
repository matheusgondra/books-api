package com.matheusgondra.books.author.controller.doc;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.doc.annotation.ApiServerErrorResponse;
import com.matheusgondra.books.doc.annotation.ApiUnauthorizedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Load Author by name", description = "Endpoint to load an author by their name.")
@ApiResponse(
        responseCode = "200",
        description = "Author found successfully",
        content = @Content(schema = @Schema(implementation = Author.class)))
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoadAuthorByNameControllerDoc {}
