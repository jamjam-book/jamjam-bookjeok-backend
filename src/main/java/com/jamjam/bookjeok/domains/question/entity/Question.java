package com.jamjam.bookjeok.domains.question.entity;

import com.jamjam.bookjeok.domains.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "writer_uid", nullable = false)
    private Long writerUid;

    @Column(name = "question_categories_id", nullable = false)
    private Long questionCategoriesId;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "questions_img_url")
    private String questionsImgUrl;

    @Builder
    public Question(
            Long writerUid, Long questionCategoriesId,
            String title, String contents, LocalDateTime createdAt,
            LocalDateTime modifiedAt, boolean isDeleted, String questionsImgUrl
    ) {
        this.writerUid = writerUid;
        this.questionCategoriesId = questionCategoriesId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
        this.questionsImgUrl = questionsImgUrl;
    }

}