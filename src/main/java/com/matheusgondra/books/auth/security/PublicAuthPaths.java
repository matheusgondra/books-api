package com.matheusgondra.books.auth.security;

import org.springframework.util.AntPathMatcher;

public class PublicAuthPaths {
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static final String[] PUBLIC_ENDPOINTS = {
        "/api/signup",
        "/api/login",
        "/api-docs*",
        "/api-docs/**",
        "/docs",
        "/docs/**",
        "/**/favicon.svg",
        "/**/favicon.ico",
        "/actuator",
        "/actuator/**"
    };

    public static boolean isPublicRoute(String route) {
        for (String publicEndpoint : PUBLIC_ENDPOINTS) {
            if (pathMatcher.match(publicEndpoint, route)) {
                return true;
            }
        }

        return false;
    }
}
