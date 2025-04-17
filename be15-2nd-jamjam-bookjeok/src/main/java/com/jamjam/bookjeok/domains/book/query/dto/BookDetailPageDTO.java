package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class BookDetailPageDTO {

    private Long bookId;
    private String bookInfo;
    private BookCategoryDTO bookCategory;
    private PublisherDTO publisher;
    private List<ReviewDTO> reviews;
    private String bookName;
    private String isbn;
    private LocalDateTime publishedAt;
    private int price;
    private int stockQuantity;
    private String imageUrl;
    private List<AuthorDTO> authors;
    private int interestCount;

    public BookDetailPageDTO(Long bookId, String bookInfo, BookCategoryDTO bookCategory,
                             PublisherDTO publisher, List<ReviewDTO> reviews, String bookName, String isbn,
                             LocalDateTime publishedAt, int price, int stockQuantity, String imageUrl, List<AuthorDTO> authors, int interestCount) {
        this.bookId = bookId;
        this.bookInfo = bookInfo;
        this.bookCategory = bookCategory;
        this.publisher = publisher;
        this.reviews = reviews;
        this.bookName = bookName;
        this.isbn = isbn;
        this.publishedAt = publishedAt;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
        this.authors = authors;
        this.interestCount = interestCount;
    }


}
