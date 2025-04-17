package com.jamjam.bookjeok.domains.question.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "question_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_category_id")
    private Long questionCategoryId;

    @Column(name = "question_category_name")
    private String questionCategoryName;

    @Builder
    public QuestionCategory(String questionCategoryName) {
        this.questionCategoryName = questionCategoryName;
    }

}