package com.jamjam.bookjeok.domains.community.entity;

import com.jamjam.bookjeok.domains.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long post_id;

    @Column(name = "writer_uid", nullable = false)
    private Long writerUid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid = false;

    @Column(name = "created_At", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Builder
    public Post(
            Long writerUid, String title, String content, boolean isValid,
            LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isDeleted
    ) {
        this.writerUid = writerUid;
        this.title = title;
        this.content = content;
        this.isValid = isValid;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

}