package com.matheusgondra.books.book.controller.doc;

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

@Operation(summary = "Delete a book by its ID", description = "Endpoint to delete a book using its unique identifier")
@ApiResponse(
        responseCode = "204",
        description = "Book deleted successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)))
@ApiNotFoundResponse
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DeleteBookControllerDoc {}
