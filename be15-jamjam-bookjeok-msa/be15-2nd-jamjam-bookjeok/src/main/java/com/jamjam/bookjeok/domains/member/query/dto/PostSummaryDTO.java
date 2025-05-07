package com.jamjam.bookjeok.domains.member.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDTO {

    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createdAt;

}
