package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "bookmarks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "member_uid", nullable = false)
    private Long memberUid;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Bookmark(Long postId, Long memberUid, LocalDateTime createdAt) {
        this.postId = postId;
        this.memberUid = memberUid;
        this.createdAt = createdAt;
    }

}