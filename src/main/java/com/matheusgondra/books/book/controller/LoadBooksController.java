package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.controller.doc.LoadBooksControllerDoc;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.usecase.load.LoadBooks;
import com.matheusgondra.books.doc.annotation.BookTag;
import com.matheusgondra.books.doc.annotation.SecurityJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@BookTag
@SecurityJWT
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class LoadBooksController {
    private final LoadBooks useCase;

    @LoadBooksControllerDoc
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PagedModel<BookDetails>> handle(Pageable pageable) {
        log.debug("Pageable: {}", pageable);

        Page<BookDetails> result = this.useCase.execute(pageable);

        log.debug("Use case result: {}", result);

        PagedModel<BookDetails> pagedResponse = new PagedModel<>(result);

        return ResponseEntity.ok(pagedResponse);
    }
}
