package com.jamjam.bookjeok.domains.question.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "question_answers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionAnswer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "writer_uid")
    private Long writerUid;

    @Lob
    @Column(name = "contents")
    private String contents;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Builder
    public QuestionAnswer(
            Long questionId, Long writerUid, String contents,
            LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isDeleted
    ) {
        this.questionId = questionId;
        this.writerUid = writerUid;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

    public void updateAnswer(final String contents, final LocalDateTime modifiedAt) {
        this.contents = contents;
        this.modifiedAt = modifiedAt;
    }

    public void deleteAnswer(final LocalDateTime modifiedAt, final boolean isDeleted) {
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

}