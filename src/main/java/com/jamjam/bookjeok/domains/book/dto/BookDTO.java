package com.jamjam.bookjeok.domains.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BookDTO {

    private String authorName;
    private String publisherName;
    private String categoryName;
    private String isbn;
    private LocalDateTime publishedAt;
    private int price;
    private int stockQuantity;
    private String imageUrl;
}
