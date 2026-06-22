package com.matheusgondra.books.book.controller;

import com.matheusgondra.books.book.controller.doc.RegisterBookControllerDoc;
import com.matheusgondra.books.book.dto.request.RegisterBookRequestDTO;
import com.matheusgondra.books.book.dto.response.RegisterBookResponseDTO;
import com.matheusgondra.books.book.usecase.register.RegisterBook;
import com.matheusgondra.books.book.usecase.register.RegisterBookData;
import com.matheusgondra.books.book.usecase.register.RegisterBookResult;
import com.matheusgondra.books.doc.annotation.BookTag;
import com.matheusgondra.books.doc.annotation.SecurityJWT;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@BookTag
@SecurityJWT
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book/register")
public class RegisterBookController {
    private final RegisterBook useCase;

    @RegisterBookControllerDoc
    @PostMapping
    public ResponseEntity<RegisterBookResponseDTO> handle(@RequestBody @Valid RegisterBookRequestDTO dto) {
        log.debug("DTO: {}", dto);

        RegisterBookData data = new RegisterBookData(dto.title(), dto.author(), dto.isbn(), dto.pages());
        RegisterBookResult result = this.useCase.execute(data);

        log.debug("UseCase result: {}", result);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(result.id())
                .toUri();

        return ResponseEntity.created(location).body(new RegisterBookResponseDTO(result));
    }
}
