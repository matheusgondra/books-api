package com.matheusgondra.books.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDTO(@JsonProperty("access_token") String accessToken) {

}
