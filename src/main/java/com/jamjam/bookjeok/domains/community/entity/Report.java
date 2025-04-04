package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    private long reportid;

    @Id
    private long postid;

    @Id
    private long commentid;

    @Id
    private long reporteruid;
    private String reportReason;
    private LocalDateTime reportedAt;

}
