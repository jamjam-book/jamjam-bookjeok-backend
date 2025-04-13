package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.*;
import com.jamjam.bookjeok.domains.book.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.dto.response.ReviewResponse;
import com.jamjam.bookjeok.domains.book.entity.Review;
import com.jamjam.bookjeok.domains.book.repository.ReviewRepository;
import com.jamjam.bookjeok.domains.book.repository.mapper.BookMapper;
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
public class BookMemberServiceImpl implements BookMemberService{

    private final BookMapper bookMapper;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public List<BookDetailDTO> getBookList(Map<String, Object> params) {
        List<BookDetailDTO> books = bookMapper.findBookListOrderByOption(params);

        for (BookDetailDTO book : books) {
            if (book.getAuthorNames() != null) {
                List<AuthorDTO> authors = Arrays.stream(book.getAuthorNames().split(",\\s*"))
                        .map(AuthorDTO::new)
                        .toList();
            }
        }
        return books;
    }

    @Override
    @Transactional
    public BookDetailPageDTO getBookDetail(Map<String, Object> params) {

        BookDetailPageDTO book =  bookMapper.getBookDetail(params);
        params.put("bookId", book.getBookId());
        List<AuthorDTO> authors = bookMapper.getAuthors(params);
        List<ReviewDTO> reviews = bookMapper.getReviews(params);

        return new BookDetailPageDTO(
                book.getBookId(), book.getBookInfo(), book.getBookCategory(), book.getPublisher(),
                reviews, book.getBookName(), book.getIsbn(), book.getPublishedAt(), book.getPrice(),
                book.getStockQuantity(), book.getImageUrl(), authors, book.getInterestCount());

    }

    @Override
    @Transactional
    public List<PopularBookDTO> getPopularBooks() {
        List<PopularBookDTO> books = bookMapper.getPopularBooks();
        Map<String, Object> params = new HashMap<>();

        List<PopularBookDTO> result = new ArrayList<>();

        for (PopularBookDTO book : books) {

            params.put("bookId", book.getBookId());
            List<AuthorDTO> authors = bookMapper.getAuthors(params);

            result.add(new PopularBookDTO(
                    book.getBookId(), book.getBookName(),
                    book.getImageUrl(), book.getIsbn(),book.getPublishedAt(),
                    book.getTotalQuantity(), book.getPublisher(), authors
            ));
        }

        return result;
    }

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

    @Override
    @Transactional
    public boolean validCheckBuyer(ReviewRequest request) {

        Map<String, Object> params = new HashMap<>();
        params.put("memberUid", request.memberUid());
        params.put("bookId", request.bookId());

        Long memberUid = bookMapper.validCheckReviewer(params);

        return memberUid != null;

    }

}
