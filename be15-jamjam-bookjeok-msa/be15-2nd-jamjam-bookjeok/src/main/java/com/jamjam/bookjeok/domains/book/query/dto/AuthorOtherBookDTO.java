package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorOtherBookDTO {
    private Long bookId;
    private Long authorId;
    private String bookName;
    private String imageUrl;
    private int price;
}

