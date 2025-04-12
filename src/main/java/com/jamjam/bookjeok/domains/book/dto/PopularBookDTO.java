package com.jamjam.bookjeok.domains.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PopularBookDTO {

    private Long bookId;
    private String bookName;
    private String imageUrl;
    private String isbn;
    private LocalDate publishedAt;
    private int totalQuantity;

    private PublisherDTO publisher;
    private List<AuthorDTO> authors;
}
