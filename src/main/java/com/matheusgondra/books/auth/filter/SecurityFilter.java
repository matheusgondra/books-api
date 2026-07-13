package com.matheusgondra.books.auth.filter;

import com.matheusgondra.books.auth.security.PublicAuthPaths;
import com.matheusgondra.books.cryptography.service.TokenService;
import com.matheusgondra.books.exception.response.ErrorResponse;
import com.matheusgondra.books.user.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.xml.XmlMapper;

@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return PublicAuthPaths.isPublicRoute(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            writeUnauthorizedResponse(request, response);
            return;
        }

        String token = authorizationHeader.substring("Bearer ".length()).trim();
        if (token.isBlank()) {
            writeUnauthorizedResponse(request, response);
            return;
        }

        String subject = tokenService.validateToken(token);
        if (subject == null) {
            writeUnauthorizedResponse(request, response);
            return;
        }

        UUID userId = UUID.fromString(subject);
        UserDetails user = User.builder().id(userId).build();

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void writeUnauthorizedResponse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        if (acceptHeader != null && acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            response.setContentType(MediaType.APPLICATION_XML_VALUE);
            response.getWriter().write(xmlMapper.writeValueAsString(errorResponse));
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }
}
