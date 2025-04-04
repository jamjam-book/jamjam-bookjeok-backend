package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "")
public class Bookmark {

    @Id
    private long bookmarkid;
    private long postid;
    private long memberuid;

    private LocalDateTime createdAt;

}
