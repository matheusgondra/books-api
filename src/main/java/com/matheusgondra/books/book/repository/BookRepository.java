package com.matheusgondra.books.book.repository;

import com.matheusgondra.books.book.model.Book;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(String isbn);

    @EntityGraph(attributePaths = "author")
    Optional<Book> findWithAuthorById(UUID id);
}
