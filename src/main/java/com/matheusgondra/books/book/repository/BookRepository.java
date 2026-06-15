package com.matheusgondra.books.book.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusgondra.books.book.model.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
