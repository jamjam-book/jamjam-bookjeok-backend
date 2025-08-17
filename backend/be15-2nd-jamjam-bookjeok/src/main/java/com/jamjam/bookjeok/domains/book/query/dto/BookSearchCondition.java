package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookSearchCondition {
    private List<Long> categoryIds;
    private String keyword;
    private String keywordType;
    private String sort;
    private Integer minPrice;
    private Integer maxPrice;

    private Integer page = 0;
    private Integer size = 10;

    public int getOffset() {
        return page * size;
    }

    public int getLimit() {
        return size;
    }
}
