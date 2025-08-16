package com.jamjam.bookjeok.domains.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "authors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "author_name")
    private String authorName;

    @Builder
    public Author(String authorName) {
        this.authorName = authorName;
    }

}