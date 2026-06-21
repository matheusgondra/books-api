package com.matheusgondra.books.book.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusgondra.books.book.model.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(String isbn);

    @EntityGraph(attributePaths = "author")
    Optional<Book> findWithAuthorById(UUID id);
}
