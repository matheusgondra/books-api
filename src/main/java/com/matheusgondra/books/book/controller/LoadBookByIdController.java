package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.controller.doc.LoadBookByIdControllerDoc;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.usecase.load.LoadBookById;
import com.matheusgondra.books.doc.annotation.SecurityJWT;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book")
@SecurityJWT
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class LoadBookByIdController {
    private final LoadBookById useCase;

    @LoadBookByIdControllerDoc
    @GetMapping("{id}")
    public ResponseEntity<BookDetails> handle(@PathVariable UUID id) {
        BookDetails result = this.useCase.execute(id);

        log.debug("UseCase result: {}", result);

        return ResponseEntity.ok(result);
    }
}
