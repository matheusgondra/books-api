package com.matheusgondra.books.author.controller;

import com.matheusgondra.books.author.controller.doc.RegisterAuthorControllerDoc;
import com.matheusgondra.books.author.dto.request.RegisterAuthorRequestDTO;
import com.matheusgondra.books.author.dto.response.RegisterAuthorResponseDTO;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthor;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthorData;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthorResponse;
import com.matheusgondra.books.doc.annotation.SecurityJWT;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityJWT
@Tag(name = "Author")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/author/register")
public class RegisterAuthorController {
    private final RegisterAuthor useCase;

    @RegisterAuthorControllerDoc
    @PostMapping
    public ResponseEntity<RegisterAuthorResponseDTO> handle(@RequestBody @Valid RegisterAuthorRequestDTO dto) {
        log.debug("DTO: {}", dto);

        RegisterAuthorData data = new RegisterAuthorData(dto.name());
        RegisterAuthorResponse result = useCase.execute(data);

        log.debug("Use case result: {}", result);

        RegisterAuthorResponseDTO response =
                new RegisterAuthorResponseDTO(result.id(), result.name(), result.createdAt(), result.updatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
