package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long reviewId;
    private Long memberUid;
    private Long bookId;
    private String content;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String activityStatus;

}
