package com.matheusgondra.books.book.controller.doc;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.doc.annotation.ApiNotFoundResponse;
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
import org.springframework.http.MediaType;

@Operation(
        summary = "Load a book by its ID",
        description = "Endpoint to retrieve a book's details using its unique identifier")
@ApiResponse(
        responseCode = "200",
        description = "Book loaded successfully",
        content =
                @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = BookDetails.class)))
@ApiNotFoundResponse
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoadBookByIdControllerDoc {}
