package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    private long postid;

    @Id
    private long writerid;

    private String title;
    private String content;
    private int isValid;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int isDeleted;

}
