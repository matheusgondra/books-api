package com.matheusgondra.books.doc.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApiResponse(
        responseCode = "409",
        description = "Conflict",
        content =
                @Content(
                        mediaType = "application/json",
                        examples = {@ExampleObject(name = "error", value = "{\"status\":409,\"message\":\"Conflict\"}")
                        }))
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ApiConflictResponse {}
