package com.matheusgondra.books.book.usecase.update;

import com.matheusgondra.books.book.dto.BookDetails;
import java.util.UUID;

public interface UpdateBook {
    BookDetails execute(UUID id, UpdateBookData data);
}
