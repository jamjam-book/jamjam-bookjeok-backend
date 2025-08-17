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
public class QuestionListDTO {

    private Long questionId;
    private Long writerUid;
    private String writerName;
    private Long questionCategoriesId;
    private String questionCategoryName;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
