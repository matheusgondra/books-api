package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.dto.request.UpdateBookRequestDTO;
import com.matheusgondra.books.book.usecase.update.UpdateBook;
import com.matheusgondra.books.book.usecase.update.UpdateBookData;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class UpdateBookController {
    private final UpdateBook useCase;

    @PutMapping("{id}")
    public ResponseEntity<BookDetails> handle(@PathVariable UUID id, @RequestBody @Valid UpdateBookRequestDTO dto) {
        log.debug("Update book {} with data {}", id, dto);

        UpdateBookData data = new UpdateBookData(dto.title(), dto.author(), dto.isbn(), dto.pages());
        BookDetails result = this.useCase.execute(id, data);

        log.debug("Usecase result: {}", result);

        return ResponseEntity.ok(result);
    }
}
