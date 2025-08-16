package com.jamjam.bookjeok.domains.book.command.dto;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NaverResultDTO {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<BookApiDTO> items;
}
