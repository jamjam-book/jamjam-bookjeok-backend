package com.jamjam.bookjeok.domains.book.repository;

import com.jamjam.bookjeok.domains.book.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByReviewId(Long reviewId);
}
