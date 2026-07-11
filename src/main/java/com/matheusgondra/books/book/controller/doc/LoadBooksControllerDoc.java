package com.matheusgondra.books.book.controller.doc;

import com.matheusgondra.books.doc.annotation.ApiNotFoundResponse;
import com.matheusgondra.books.doc.annotation.ApiServerErrorResponse;
import com.matheusgondra.books.doc.annotation.ApiUnauthorizedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Load books", description = "Endpoint to retrieve a list of books")
@ApiResponse(responseCode = "200", description = "Books loaded successfully")
@ApiNotFoundResponse
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoadBooksControllerDoc {}
