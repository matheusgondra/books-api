package com.matheusgondra.books.book.controller.doc;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.doc.annotation.ApiBadRequestResponse;
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

@Operation(summary = "Update a book", description = "Endpoint to update an existing book in the system")
@ApiResponse(
        responseCode = "200",
        description = "Book updated successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDetails.class)))
@ApiBadRequestResponse
@ApiNotFoundResponse
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UpdateBookControllerDoc {}
