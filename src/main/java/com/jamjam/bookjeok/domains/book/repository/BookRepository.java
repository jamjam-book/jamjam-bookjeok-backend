package com.jamjam.bookjeok.domains.book.repository;

import com.jamjam.bookjeok.domains.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookByIsbn(String isbn);
}
