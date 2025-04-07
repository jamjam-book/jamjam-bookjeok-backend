package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "writer_uid", nullable = false)
    private Long writerUid;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Builder
    public Comment(
            Long writerUid, Long postId, String contents, boolean isValid,
            LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isDeleted
    ) {
        this.writerUid = writerUid;
        this.postId = postId;
        this.contents = contents;
        this.isValid = isValid;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

}