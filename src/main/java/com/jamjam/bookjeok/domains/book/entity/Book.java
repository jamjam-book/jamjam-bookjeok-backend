package com.jamjam.bookjeok.domains.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "publisher_id")
    private Long publisherId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "book_name")
    private String bookName;

    @Lob
    @Column(name = "book_info")
    private String bookInfo;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "published_at")
    private LocalDate publishedAt;

    @Column(name = "price")
    private int price;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Builder
    public Book(
            Long publisherId, Long categoryId, String bookName, String bookInfo,
            String isbn, String imageUrl, LocalDate publishedAt, int price,
            int stockQuantity, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isDeleted
    ) {
        this.publisherId = publisherId;
        this.categoryId = categoryId;
        this.bookName = bookName;
        this.bookInfo = bookInfo;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateBook(final Long publisherId, final Long categoryId, final String bookName, final String bookInfo,
                           final String isbn, final LocalDate publishedAt, final int price,
                           final int stockQuantity, final LocalDateTime modifiedAt) {
        this.publisherId = publisherId;
        this.categoryId = categoryId;
        this.bookName = bookName;
        this.bookInfo = bookInfo;
        this.isbn = isbn;
        this.publishedAt = publishedAt;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.modifiedAt = modifiedAt;
    }

    public void deleteBook(final boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}