package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Like {

    @Id
    private long likeid;

    @Id
    private long memberuid;

    @Id
    private long postid;

    @Id
    private long commentid;

}
