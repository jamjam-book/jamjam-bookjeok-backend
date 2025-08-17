package com.jamjam.bookjeok.domains.book.command.repository;

import com.jamjam.bookjeok.domains.book.command.entity.BookAuthor;
import com.jamjam.bookjeok.domains.book.command.entity.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
}
