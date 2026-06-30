package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.controller.doc.LoadBooksControllerDoc;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.usecase.load.LoadBooks;
import com.matheusgondra.books.doc.annotation.BookTag;
import com.matheusgondra.books.doc.annotation.SecurityJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping
    public ResponseEntity<PagedModel<BookDetails>> handle(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        log.debug("Page {}, Page size {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        Page<BookDetails> result = this.useCase.execute(pageable);

        log.debug("Use case result: {}", result);

        PagedModel<BookDetails> pagedResponse = new PagedModel<>(result);

        return ResponseEntity.ok(pagedResponse);
    }
}
