package com.matheusgondra.books.auth.controller.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.matheusgondra.books.doc.annotation.ApiBadRequestResponse;
import com.matheusgondra.books.doc.annotation.ApiServerErrorResponse;
import com.matheusgondra.books.doc.annotation.ApiUnauthorizedResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Operation(summary = "Login", description = "Endpoint for user authentication. Validates user credentials and returns a JWT token if successful.")
@ApiResponse(responseCode = "200", description = "User successfully authenticated, JWT token returned")
@ApiBadRequestResponse
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginControllerDoc {

}
