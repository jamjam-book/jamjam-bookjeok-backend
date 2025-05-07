package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookListPageDTO {
    private List<BookDetailDTO> content;
    private boolean last;
}
