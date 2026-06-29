package com.matheusgondra.books.book.usecase.load;

import com.matheusgondra.books.book.dto.BookDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoadBooks {
    Page<BookDetails> execute(Pageable pageable);
}
