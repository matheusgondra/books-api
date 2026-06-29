package com.matheusgondra.books.book.service;

import com.matheusgondra.books.book.repository.BookRepository;
import com.matheusgondra.books.book.usecase.delete.DeleteBook;
import com.matheusgondra.books.exception.BookNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteBookService implements DeleteBook {
    private final BookRepository repository;

    @Override
    public void execute(UUID id) {
        this.repository.findById(id).orElseThrow(BookNotFoundException::new);

        repository.deleteById(id);
    }
}
