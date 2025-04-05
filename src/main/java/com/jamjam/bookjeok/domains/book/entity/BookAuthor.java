package com.jamjam.bookjeok.domains.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_authors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookAuthor {
    // PK 두개

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    //@Id
    private Long authorId;

}
