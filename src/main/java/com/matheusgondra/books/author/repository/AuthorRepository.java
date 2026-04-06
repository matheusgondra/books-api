package com.matheusgondra.books.author.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheusgondra.books.author.model.Author;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
