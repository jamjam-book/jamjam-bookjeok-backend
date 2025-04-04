package com.jamjam.bookjeok.domains.member.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

}
