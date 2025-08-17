package com.jamjam.bookjeok.domains.book.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.ReviewResponse;
import com.jamjam.bookjeok.domains.book.command.service.BookCommandMemberService;
import com.jamjam.bookjeok.domains.book.query.service.BookQueryMemberService;
import com.jamjam.bookjeok.exception.book.BookErrorCode;
import com.jamjam.bookjeok.exception.book.review.InconsistentReviewException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookCommandMemberController {

    private final BookCommandMemberService bookCommandMemberService;
    private final BookQueryMemberService bookQueryMemberService;

    @PostMapping("/book/{bookId}/review")
    public ResponseEntity<ApiResponse<ReviewResponse>> writeReview(@RequestBody @Validated ReviewRequest reviewRequest) {

        log.info("reviewRequest, {}", reviewRequest.toString());

        boolean isBuyer = bookQueryMemberService.validCheckBuyer(reviewRequest);

        if(!isBuyer) {
            throw new InconsistentReviewException(BookErrorCode.INVALID_WRITER);
        }

        ReviewResponse response = bookCommandMemberService.writeReview(reviewRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/book/{bookId}/review/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> modifyReview(
            @RequestBody @Validated ReviewRequest reviewRequest,
            @PathVariable Long reviewId){

        ReviewResponse response = bookCommandMemberService.modifyReview(reviewRequest, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/book/{bookId}/review/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId) {

        bookCommandMemberService.deleteReview(reviewId);

        return ResponseEntity
                .ok(ApiResponse.success(null));

    }

}
