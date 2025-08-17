package com.jamjam.bookjeok.domains.book.command.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Builder
    public BookAuthor(BookAuthorId bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

}