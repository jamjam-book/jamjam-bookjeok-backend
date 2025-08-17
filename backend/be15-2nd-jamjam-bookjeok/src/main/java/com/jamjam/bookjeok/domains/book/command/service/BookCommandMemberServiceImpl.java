package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.ReviewResponse;
import com.jamjam.bookjeok.domains.book.command.entity.Review;
import com.jamjam.bookjeok.domains.book.command.repository.ReviewRepository;
import com.jamjam.bookjeok.exception.book.BookErrorCode;
import com.jamjam.bookjeok.exception.book.review.InconsistentReviewException;
import com.jamjam.bookjeok.exception.book.review.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookCommandMemberServiceImpl implements BookCommandMemberService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ReviewResponse writeReview(ReviewRequest request) {

        Review review = Review.builder()
                .bookId(request.bookId())
                .memberUid(request.memberUid())
                .content(request.content())
                .rating(request.rating())
                .build();

        Review newReview = reviewRepository.save(review);

        return buildReviewResponse(newReview);
    }

    @Override
    @Transactional
    public ReviewResponse modifyReview(ReviewRequest request, Long reviewId) {

        Optional<Review> findReview = reviewRepository.findByReviewId(reviewId);

        if(findReview.isEmpty()) {
            throw new ReviewNotFoundException(BookErrorCode.NOT_EXIST_REVIEW);
        }

        if(!Objects.equals(findReview.get().getMemberUid(), request.memberUid())) {
            throw new InconsistentReviewException(BookErrorCode.INCONSISTENT_USER);
        }

        if(!Objects.equals(findReview.get().getBookId(), request.bookId())) {
            throw new InconsistentReviewException(BookErrorCode.INCONSISTENT_BOOK);
        }

        findReview.get().updateReview(
                request.content(),
                request.rating(),
                LocalDateTime.now()
        );

        Review modReview = reviewRepository.save(findReview.get());

        return buildReviewResponse(modReview);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {

        Optional<Review> findReview = reviewRepository.findByReviewId(reviewId);

        if(findReview.isEmpty()) {
            throw new ReviewNotFoundException(BookErrorCode.NOT_EXIST_REVIEW);
        }

        findReview.get().deleteReview(LocalDateTime.now(), true);
        reviewRepository.save(findReview.get());
    }

    private ReviewResponse buildReviewResponse(Review review) {

        return ReviewResponse.builder()
                .bookId(review.getBookId())
                .bookId(review.getBookId())
                .memberUid(review.getMemberUid())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

}
