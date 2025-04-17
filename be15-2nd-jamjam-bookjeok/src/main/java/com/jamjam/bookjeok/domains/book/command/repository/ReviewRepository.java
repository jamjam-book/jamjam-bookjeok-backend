package com.jamjam.bookjeok.domains.book.command.repository;

import com.jamjam.bookjeok.domains.book.command.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByReviewId(Long reviewId);
}
