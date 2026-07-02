package com.matheusgondra.books.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("response")
public record LoginResponseDTO(String accessToken) {}
