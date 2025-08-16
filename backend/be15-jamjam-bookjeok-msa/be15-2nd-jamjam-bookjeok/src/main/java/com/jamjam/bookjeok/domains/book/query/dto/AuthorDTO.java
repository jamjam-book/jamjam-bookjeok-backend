package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Long authorId;
    private String authorName;

    public AuthorDTO(String authorName) {
        this.authorName = authorName;
    }

}
