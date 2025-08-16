package com.jamjam.bookjeok.domains.book.repository;

import com.jamjam.bookjeok.domains.book.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByAuthorName(String author);
}
