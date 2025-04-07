package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "reporter_uid", nullable = false)
    private Long reporterUid;

    @Column(name = "report_reason", nullable = false)
    private String reportReason;

    @Column(name = "reported_at", nullable = false)
    private LocalDateTime reportedAt;

    @Builder
    public Report(
            Long postId, Long commentId, Long reporterUid,
            String reportReason, LocalDateTime reportedAt
    ) {
        this.postId = postId;
        this.commentId = commentId;
        this.reporterUid = reporterUid;
        this.reportReason = reportReason;
        this.reportedAt = reportedAt;
    }
}