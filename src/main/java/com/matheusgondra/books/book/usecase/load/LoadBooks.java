package com.matheusgondra.books.book.usecase.load;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;

import com.matheusgondra.books.book.dto.BookDetails;

public interface LoadBooks {
    Page<BookDetails> execute(Pageable pageable);
}
