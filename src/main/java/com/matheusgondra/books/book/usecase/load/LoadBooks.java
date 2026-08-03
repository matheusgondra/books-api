package com.matheusgondra.books.book.usecase.load;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoadBooks {
    Page<BookDetails> execute(User owner, Pageable pageable);
}
