package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.usecase.load.LoadBookById;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class LoadBookByIdController {
    private final LoadBookById useCase;

    @GetMapping("{id}")
    public ResponseEntity<BookDetails> handle(@PathVariable UUID id) {
        BookDetails result = this.useCase.execute(id);

        log.debug("UseCase result: {}", result);

        return ResponseEntity.ok(result);
    }
}
