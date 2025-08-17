package com.jamjam.bookjeok.domains.book.command.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookApiDTO {

    private String title;
    private String link;
    private String image;
    private String author;
    private String discount;
    private String publisher;
    private String pubdate;
    private String isbn;
    private String description;
    private String toc;
    private String categoryName;

    public BookApiDTO(String toc, String categoryName) {
        this.toc = toc;
        this.categoryName = categoryName;
    }
}
