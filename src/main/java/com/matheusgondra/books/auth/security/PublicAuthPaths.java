package com.matheusgondra.books.auth.security;

import java.util.List;

public class PublicAuthPaths {
    public static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/signup",
            "/api/login");

    public static boolean isPublicRoute(String route) {
        return PUBLIC_ENDPOINTS.contains(route);
    }
}
