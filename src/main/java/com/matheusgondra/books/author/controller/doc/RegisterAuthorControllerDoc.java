package com.matheusgondra.books.author.controller.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.matheusgondra.books.author.dto.response.RegisterAuthorResponseDTO;
import com.matheusgondra.books.doc.annotation.ApiConflictResponse;
import com.matheusgondra.books.doc.annotation.ApiServerErrorResponse;
import com.matheusgondra.books.doc.annotation.ApiUnauthorizedResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Operation(summary = "Register a new author", description = "Endpoint to register a new author in the system")
@ApiResponse(responseCode = "201", description = "Author registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterAuthorResponseDTO.class)))
@ApiConflictResponse
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RegisterAuthorControllerDoc {
}
