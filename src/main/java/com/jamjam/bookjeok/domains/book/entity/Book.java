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

    @Column(name = "publisher_id", nullable = false)
    private Long publisherId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "published_at", nullable = false)
    private LocalDate publishedAt;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Builder
    public Book(
            Long publisherId, Long categoryId, String bookName,
            String isbn, String imageUrl, LocalDate publishedAt, int price,
            int stockQuantity, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isDeleted
    ) {
        this.publisherId = publisherId;
        this.categoryId = categoryId;
        this.bookName = bookName;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

}