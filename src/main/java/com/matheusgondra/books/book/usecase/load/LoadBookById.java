package com.matheusgondra.books.book.usecase.load;

import com.matheusgondra.books.book.dto.BookDetails;
import java.util.UUID;

public interface LoadBookById {
    BookDetails execute(UUID id);
}
