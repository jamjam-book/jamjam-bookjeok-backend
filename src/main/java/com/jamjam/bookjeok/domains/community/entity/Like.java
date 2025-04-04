package com.jamjam.bookjeok.domains.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Like {

    @Id
    private long likeid;
    private long memberuid;
    private long postid;
    private long commentid;

}
