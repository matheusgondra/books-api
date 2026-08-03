package com.matheusgondra.books.book.usecase.delete;

import com.matheusgondra.books.user.model.User;
import java.util.UUID;

public interface DeleteBook {
    void execute(User owner, UUID id);
}
