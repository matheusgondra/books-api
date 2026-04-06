package com.matheusgondra.books.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusgondra.books.auth.dto.request.LoginRequestDTO;
import com.matheusgondra.books.auth.dto.response.LoginResponseDTO;
import com.matheusgondra.books.auth.usecase.login.Login;
import com.matheusgondra.books.auth.usecase.login.LoginData;
import com.matheusgondra.books.auth.usecase.login.LoginResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final Login useCase;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> handle(@RequestBody @Valid LoginRequestDTO dto) {
        LoginData loginData = new LoginData(dto.email(), dto.password());

        LoginResponse response = this.useCase.execute(loginData);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(response.token());

        return ResponseEntity.ok(loginResponseDTO);
    }
}
