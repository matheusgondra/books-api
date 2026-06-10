package com.matheusgondra.books.author.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusgondra.books.author.controller.doc.LoadAuthorByNameControllerDoc;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.usecase.load.author.LoadAuthorByName;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Author")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/author/loadByName")
public class LoadAuthorByNameController {
    private final LoadAuthorByName useCase;

    @LoadAuthorByNameControllerDoc
    @GetMapping("{name}")
    public ResponseEntity<Author> handle(@PathVariable String name) {
        log.debug("Receive name: {}", name);

        Author result = this.useCase.execute(name);

        log.debug("Use case result: {}", result);

        return ResponseEntity.ok(result);
    }
}
