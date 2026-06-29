package com.matheusgondra.books.auth.usecase.login;

public interface Login {
    LoginResponse execute(LoginData data);
}
