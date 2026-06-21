package com.matheusgondra.books.book.usecase.delete;

import java.util.UUID;

public interface DeleteBook {
    void execute(UUID id);
}
