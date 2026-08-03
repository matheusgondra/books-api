package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.controller.doc.LoadBookByIdControllerDoc;
import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.book.usecase.load.LoadBookById;
import com.matheusgondra.books.doc.annotation.BookTag;
import com.matheusgondra.books.doc.annotation.SecurityJWT;
import com.matheusgondra.books.user.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@BookTag
@SecurityJWT
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class LoadBookByIdController {
    private final LoadBookById useCase;

    @LoadBookByIdControllerDoc
    @GetMapping(
            value = "{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BookDetails> handle(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        BookDetails result = this.useCase.execute(user, id);

        log.debug("UseCase result: {}", result);

        return ResponseEntity.ok(result);
    }
}
