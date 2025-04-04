package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    private long reportid;
    private long postid;
    private long commentid;
    private long reporteruid;
    private String reportReason;
    private LocalDateTime reportedAt;

}
