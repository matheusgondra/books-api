package com.matheusgondra.books.book.controller.doc;

import com.matheusgondra.books.book.dto.response.RegisterBookResponseDTO;
import com.matheusgondra.books.doc.annotation.ApiBadRequestResponse;
import com.matheusgondra.books.doc.annotation.ApiConflictResponse;
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

@Operation(summary = "Register a new book", description = "Endpoint to register a new book in the system")
@ApiResponse(
        responseCode = "201",
        description = "Book registered successfully",
        content = @Content(schema = @Schema(implementation = RegisterBookResponseDTO.class)))
@ApiBadRequestResponse
@ApiConflictResponse
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RegisterBookControllerDoc {}
