package com.matheusgondra.books.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusgondra.books.book.dto.request.RegisterBookRequestDTO;
import com.matheusgondra.books.book.dto.response.RegisterBookResponseDTO;
import com.matheusgondra.books.book.usecase.register.RegisterBook;
import com.matheusgondra.books.book.usecase.register.RegisterBookData;
import com.matheusgondra.books.book.usecase.register.RegisterBookResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book/register")
public class RegisterBookController {
    private final RegisterBook useCase;

    @PostMapping
    public ResponseEntity<RegisterBookResponseDTO> handle(@RequestBody @Valid RegisterBookRequestDTO dto) {
        log.debug("DTO: {}", dto);

        RegisterBookData data = new RegisterBookData(dto.title(), dto.author(), dto.isbn(), dto.pages());
        RegisterBookResult result = this.useCase.execute(data);

        log.debug("UseCase result: {}", result);

        return ResponseEntity.created(null).body(new RegisterBookResponseDTO(result));
    }
}
