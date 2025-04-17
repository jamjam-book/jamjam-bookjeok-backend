package com.jamjam.bookjeok.domains.book.command.repository;

import com.jamjam.bookjeok.domains.book.command.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    Optional<BookCategory> findCategoryByCategoryName(String categoryName);

}
