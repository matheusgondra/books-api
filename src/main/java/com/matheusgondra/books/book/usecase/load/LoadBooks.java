package com.matheusgondra.books.book.usecase.load;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matheusgondra.books.book.dto.BookDetails;

public interface LoadBooks {
    Page<BookDetails> execute(Pageable pageable);
}
