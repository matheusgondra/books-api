package com.matheusgondra.books.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusgondra.books.auth.dto.request.SignupRequestDTO;
import com.matheusgondra.books.auth.dto.response.SignupResponseDTO;
import com.matheusgondra.books.auth.usecase.register.user.RegisterUser;
import com.matheusgondra.books.auth.usecase.register.user.RegisterUserData;
import com.matheusgondra.books.auth.usecase.register.user.RegisterUserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/signup")
public class SignupController {
    private final RegisterUser useCase;

    @PostMapping
    public ResponseEntity<SignupResponseDTO> handle(@RequestBody @Valid SignupRequestDTO dto) {
        log.debug("DTO: {}", dto);
        RegisterUserData data = new RegisterUserData(dto.firstName(), dto.lastName(), dto.email(), dto.password());

        RegisterUserResponse result = this.useCase.execute(data);
        log.debug("UseCase result: {}", result);
        SignupResponseDTO response = new SignupResponseDTO(
                result.id(),
                result.firstName(),
                result.lastName(),
                result.email(),
                result.createdAt(),
                result.updatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
