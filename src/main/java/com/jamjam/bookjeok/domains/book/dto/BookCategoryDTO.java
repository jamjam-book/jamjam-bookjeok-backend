package com.jamjam.bookjeok.domains.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookCategoryDTO {

    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean isDeleted;

    public BookCategoryDTO(String categoryName, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isDeleted) {
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }
}
