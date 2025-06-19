package com.sodagift.biz.common.model;

import java.util.List;

public record PagingResponse<T>(
        List<T> content,
        int totalElements,
        int totalPages,
        int size
) {

    public PagingResponse(List<T> content, long count, int size) {
        this(
                content,
                Math.toIntExact(count),
                (int) Math.ceil((double) count / size),
                size
        );
    }
}
