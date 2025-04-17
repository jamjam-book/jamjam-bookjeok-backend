package com.jamjam.bookjeok.domains.question.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {

    private Long questionId;
    private Long writerUid;
    private String memberName;
    private Long questionCategoryId;
    private String questionCategoryName;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String questionImgUrl;

}
