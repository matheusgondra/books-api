package com.matheusgondra.books.book.service;

import org.springframework.stereotype.Service;

import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.book.usecase.register.RegisterBook;
import com.matheusgondra.books.book.usecase.register.RegisterBookData;
import com.matheusgondra.books.book.usecase.register.RegisterBookResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RegisterBookService implements RegisterBook {
    private final AuthorRepository authorRepository;

    @Override
    public RegisterBookResult execute(RegisterBookData data) {
        this.authorRepository.findByName(data.author());

        return null;
    }
}
