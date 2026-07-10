package com.matheusgondra.books.helper.dto;

import java.util.List;

public record TestPagedModel<T>(List<T> content, PageMetadata page) {
    public record PageMetadata(Integer size, Integer number, Integer totalElements, Integer totalPages) {}
}
