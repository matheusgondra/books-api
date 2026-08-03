package com.matheusgondra.books.book.repository;

import com.matheusgondra.books.book.model.Book;
import com.matheusgondra.books.user.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(String isbn);

    @EntityGraph(attributePaths = "author")
    Optional<Book> findWithAuthorById(UUID id);

    Optional<Book> findWithAuthorByIdAndOwner(UUID id, User owner);

    Page<Book> findByOwner(User owner, Pageable pageable);

    Optional<Book> findByIdAndOwner(UUID id, User owner);
}
