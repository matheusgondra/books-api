package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.controller.doc.UpdateBookControllerDoc;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.dto.request.UpdateBookRequestDTO;
import com.matheusgondra.books.book.usecase.update.UpdateBook;
import com.matheusgondra.books.book.usecase.update.UpdateBookData;
import com.matheusgondra.books.doc.annotation.BookTag;
import com.matheusgondra.books.doc.annotation.SecurityJWT;
import com.matheusgondra.books.user.model.User;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@BookTag
@SecurityJWT
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class UpdateBookController {
    private final UpdateBook useCase;

    @UpdateBookControllerDoc
    @PutMapping(
            value = "{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookDetails> handle(
            @AuthenticationPrincipal User owner, @PathVariable UUID id, @RequestBody @Valid UpdateBookRequestDTO dto) {
        log.debug("Update book {} with data {}", id, dto);

        UpdateBookData data = new UpdateBookData(owner, dto.title(), dto.author(), dto.isbn(), dto.pages());
        BookDetails result = this.useCase.execute(id, data);

        log.debug("Usecase result: {}", result);

        return ResponseEntity.ok(result);
    }
}
