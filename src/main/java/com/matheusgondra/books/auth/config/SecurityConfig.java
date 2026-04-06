package com.matheusgondra.books.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.matheusgondra.books.auth.filter.SecurityFilter;
import com.matheusgondra.books.auth.security.PublicAuthPaths;
import com.matheusgondra.books.auth.service.UserDetailsServiceImpl;
import com.matheusgondra.books.cryptography.service.TokenService;
import com.matheusgondra.books.user.repository.UserRepository;

import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, SecurityFilter securityFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    for (String endpoint : PublicAuthPaths.PUBLIC_ENDPOINTS) {
                        auth.requestMatchers(endpoint).permitAll();
                    }
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    SecurityFilter securityFilter(TokenService tokenService, ObjectMapper objectMapper) {
        return new SecurityFilter(tokenService, objectMapper);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
