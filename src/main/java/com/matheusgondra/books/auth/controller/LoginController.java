package com.matheusgondra.books.auth.controller;

import com.matheusgondra.books.auth.controller.doc.LoginControllerDoc;
import com.matheusgondra.books.auth.dto.request.LoginRequestDTO;
import com.matheusgondra.books.auth.dto.response.LoginResponseDTO;
import com.matheusgondra.books.auth.usecase.login.Login;
import com.matheusgondra.books.auth.usecase.login.LoginData;
import com.matheusgondra.books.auth.usecase.login.LoginResponse;
import com.matheusgondra.books.doc.annotation.AuthTag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AuthTag
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final Login useCase;

    @LoginControllerDoc
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<LoginResponseDTO> handle(@RequestBody @Valid LoginRequestDTO dto) {
        log.debug("DTO: {}", dto);
        LoginData loginData = new LoginData(dto.email(), dto.password());

        LoginResponse response = this.useCase.execute(loginData);
        log.debug("LoginResponse: {}", response);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(response.token());

        return ResponseEntity.ok(loginResponseDTO);
    }
}
