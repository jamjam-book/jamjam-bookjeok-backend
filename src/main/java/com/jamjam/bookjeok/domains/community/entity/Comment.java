package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "")
public class Comment {

    @Id
    private long commentid;
    private long writeruid;
    private long postid;
    private String contents;
    private int is_valid;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int isDeleted;

}
