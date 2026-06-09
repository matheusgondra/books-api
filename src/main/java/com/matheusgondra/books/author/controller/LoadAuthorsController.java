package com.matheusgondra.books.author.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.usecase.load.author.LoadAuthors;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Author")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/author")
public class LoadAuthorsController {
    private final LoadAuthors useCase;

    @GetMapping
    public ResponseEntity<Page<Author>> handle(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.debug("Receive page {} and size {}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Author> result = this.useCase.execute(pageable);

        return ResponseEntity.ok(result);
    }
}
