package com.matheusgondra.books.book.usecase.update;

import com.matheusgondra.books.user.model.User;

public record UpdateBookData(User owner, String title, String author, String isbn, Integer pages) {}
