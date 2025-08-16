package com.jamjam.bookjeok.domains.book.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long bookId;
    private Long publisherId;
    private Long categoryId;
    private String bookName;
    private String bookInfo;
    private String isbn;
    private LocalDate publishedAt;
    private int price;
    private int stockQuantity;
    private String imageUrl;

    public BookDTO(Long publisherId, Long categoryId, String bookName, String bookInfo, String isbn, LocalDate publishedAt, int price, int stockQuantity, String imageUrl) {
        this.publisherId = publisherId;
        this.categoryId = categoryId;
        this.bookName = bookName;
        this.bookInfo = bookInfo;
        this.isbn = isbn;
        this.publishedAt = publishedAt;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }
}
