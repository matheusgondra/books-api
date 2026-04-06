package com.matheusgondra.books.auth.filter;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.matheusgondra.books.auth.security.PublicAuthPaths;
import com.matheusgondra.books.cryptography.service.TokenService;
import com.matheusgondra.books.exception.response.ErrorResponse;
import com.matheusgondra.books.user.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return PublicAuthPaths.isPublicRoute(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized");

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            writeUnauthorizedResponse(response, errorResponse);
            return;
        }

        String token = authorizationHeader.substring("Bearer ".length()).trim();
        if (token == null || token.isEmpty()) {
            writeUnauthorizedResponse(response, errorResponse);
            return;
        }

        String subject = tokenService.validateToken(token);
        if (subject == null) {
            writeUnauthorizedResponse(response, errorResponse);
            return;
        }

        UUID userId = UUID.fromString(subject);
        UserDetails user = User.builder()
                .id(userId)
                .build();

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void writeUnauthorizedResponse(HttpServletResponse response, ErrorResponse errorResponse)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
