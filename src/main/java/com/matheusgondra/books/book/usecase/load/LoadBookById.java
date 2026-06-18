package com.matheusgondra.books.book.usecase.load;

import java.util.UUID;

import com.matheusgondra.books.book.dto.BookDetails;

public interface LoadBookById {
    BookDetails execute(UUID id);
}
