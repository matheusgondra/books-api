package com.matheusgondra.books.book.usecase.load;

import com.matheusgondra.books.book.dto.BookDetails;
import com.matheusgondra.books.user.model.User;
import java.util.UUID;

public interface LoadBookById {
    BookDetails execute(User owner, UUID id);
}
