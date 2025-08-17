package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailDTO {

    private Long bookId;
    private String bookInfo;
    private BookCategoryDTO bookCategory;
    private PublisherDTO publisher;
    private String publisherName;
    private String bookName;
    private String isbn;
    private LocalDateTime publishedAt;
    private int price;
    private int stockQuantity;
    private String imageUrl;
    private String authorNames;
    private List<AuthorDTO> authors;
    private int interestCount;
    private int ordersCount;

    public void addList (List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public void setImage (String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

