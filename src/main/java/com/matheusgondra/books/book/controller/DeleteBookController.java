package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.controller.doc.DeleteBookControllerDoc;
import com.matheusgondra.books.book.usecase.delete.DeleteBook;
import com.matheusgondra.books.doc.annotation.BookTag;
import com.matheusgondra.books.doc.annotation.SecurityJWT;
import com.matheusgondra.books.user.model.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@BookTag
@SecurityJWT
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class DeleteBookController {
    private final DeleteBook useCase;

    @DeleteBookControllerDoc
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@AuthenticationPrincipal User owner, @PathVariable UUID id) {
        log.info("Deleting book with id: {}", id);

        this.useCase.execute(owner, id);

        return ResponseEntity.noContent().build();
    }
}
