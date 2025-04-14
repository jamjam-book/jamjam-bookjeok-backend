package com.jamjam.bookjeok.domains.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCategoryDTO {

    private Long questionCategoryId;
    private String questionCategoryName;

}
