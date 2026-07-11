package com.matheusgondra.books.auth.controller.doc;

import com.matheusgondra.books.doc.annotation.ApiBadRequestResponse;
import com.matheusgondra.books.doc.annotation.ApiConflictResponse;
import com.matheusgondra.books.doc.annotation.ApiServerErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
        summary = "Signup",
        description = "Endpoint for user registration. Creates a new user account with the provided details.")
@ApiResponse(responseCode = "201", description = "User successfully registered")
@ApiBadRequestResponse
@ApiConflictResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SignupControllerDoc {}
