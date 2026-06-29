package com.matheusgondra.books.author.service;

import com.matheusgondra.books.author.exception.AuthorAlreadyExistsException;
import com.matheusgondra.books.author.model.Author;
import com.matheusgondra.books.author.repository.AuthorRepository;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthor;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthorData;
import com.matheusgondra.books.author.usecase.register.author.RegisterAuthorResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterAuthorService implements RegisterAuthor {
    private final AuthorRepository repository;

    @Transactional
    @Override
    public RegisterAuthorResponse execute(RegisterAuthorData data) {
        repository.findByName(data.name()).ifPresent(author -> {
            throw new AuthorAlreadyExistsException();
        });

        Author author = new Author(data.name());

        author = repository.save(author);

        return new RegisterAuthorResponse(
                author.getId(), author.getName(), author.getCreatedAt(), author.getUpdatedAt());
    }
}
