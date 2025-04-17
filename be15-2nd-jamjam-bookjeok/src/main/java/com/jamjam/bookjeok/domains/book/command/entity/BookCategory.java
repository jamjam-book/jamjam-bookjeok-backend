package com.jamjam.bookjeok.domains.book.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "book_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Builder
    public BookCategory(
            Long categoryId, String categoryName, LocalDateTime createdAt,
            LocalDateTime modifiedAt, boolean isDeleted
    ) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

    public void updateCategory(final String categoryName, final LocalDateTime modifiedAt) {
        this.categoryName = categoryName;
        this.modifiedAt = modifiedAt;
    }

    public void deleteCategory(final LocalDateTime modifiedAt, final boolean isDeleted) {
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

}