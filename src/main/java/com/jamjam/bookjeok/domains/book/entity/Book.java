package com.jamjam.bookjeok.domains.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long publishers;

    @Column
    private Long bookCategories;

    @Column
    private String bookName;

    @Column
    private String isbn;

    @Column
    private String imageUrl;

    @Column
    private LocalDateTime publishedAt;

    @Column
    @ColumnDefault("0")
    private int price;

    @Column
    @ColumnDefault("0")
    private int stockQuantity;

    @Column
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;

    @Column
    @ColumnDefault("0")
    private Boolean isDeleted;


}
