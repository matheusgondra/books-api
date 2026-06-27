package com.matheusgondra.books.author.controller.doc;

import com.matheusgondra.books.doc.annotation.ApiServerErrorResponse;
import com.matheusgondra.books.doc.annotation.ApiUnauthorizedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "Load Authors", description = "Endpoint to load all authors.")
@ApiResponse(responseCode = "200", description = "Authors loaded successfully")
@Parameters({
    @Parameter(name = "page", description = "Page number for pagination (default is 0)", example = "0"),
    @Parameter(name = "size", description = "Number of items per page for pagination (default is 10)", example = "10")
})
@ApiUnauthorizedResponse
@ApiServerErrorResponse
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoadAuthorsControllerDoc {}
