package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDetailPageDTO;
import com.jamjam.bookjeok.domains.book.dto.PopularBookDTO;
import com.jamjam.bookjeok.domains.book.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.dto.response.ReviewResponse;

import java.util.List;
import java.util.Map;

public interface BookMemberService {

    List<BookDetailDTO> getBookList(Map<String, Object> params);

    BookDetailPageDTO getBookDetail(Map<String, Object> params);

    ReviewResponse writeReview(ReviewRequest reviewRequest);

    List<PopularBookDTO> getPopularBooks();

    ReviewResponse modifyReview(ReviewRequest reviewRequest, Long reviewId);

    void deleteReview(Long reviewId);

    boolean validCheckBuyer(ReviewRequest reviewRequest);
}
