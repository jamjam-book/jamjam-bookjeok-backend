package com.jamjam.bookjeok.domains.book.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "publishers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Publisher {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long publisherId;

    @Column(name = "publisher_name")
    private String publisherName;

    @Builder
    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }

}