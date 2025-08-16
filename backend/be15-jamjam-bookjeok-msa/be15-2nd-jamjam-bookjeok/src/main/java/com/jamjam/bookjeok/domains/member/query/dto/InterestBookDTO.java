package com.jamjam.bookjeok.domains.member.query.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InterestBookDTO {

    private Long bookId;
    private String bookName;
    private String bookInfo;
    private String imageUrl;
    private String authorName;

}
