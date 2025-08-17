package com.jamjam.bookjeok.domains.book.command.repository;

import com.jamjam.bookjeok.domains.book.command.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByAuthorName(String author);
}
