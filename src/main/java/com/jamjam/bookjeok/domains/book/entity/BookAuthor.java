package com.jamjam.bookjeok.domains.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "book_authors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookAuthor {

    @Id @EmbeddedId
    private BookAuthorId bookAuthorId;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Builder
    public BookAuthor(Long bookId, Long authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

}