package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.ReviewResponse;

public interface BookCommandMemberService {

    ReviewResponse writeReview(ReviewRequest reviewRequest);

    ReviewResponse modifyReview(ReviewRequest reviewRequest, Long reviewId);

    void deleteReview(Long reviewId);

}
