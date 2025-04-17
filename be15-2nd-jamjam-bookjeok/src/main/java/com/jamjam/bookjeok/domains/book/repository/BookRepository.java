package com.jamjam.bookjeok.domains.book.repository;

import com.jamjam.bookjeok.domains.book.dto.BookDTO;
import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.book.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByIsbn(String isbn);

    Optional<Book> findBookByBookId(Long aLong);
}
