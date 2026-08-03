package com.matheusgondra.books.book.usecase.register;

import com.matheusgondra.books.user.model.User;

public record RegisterBookData(User owner, String title, String author, String isbn, Integer pages) {}
