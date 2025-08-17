package com.jamjam.bookjeok.domains.book.command.service;


import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.ReviewResponse;
import com.jamjam.bookjeok.exception.book.review.InconsistentReviewException;
import com.jamjam.bookjeok.exception.book.review.ReviewNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class BookCommandMemberServiceImplTest {

    @Autowired
    BookCommandMemberService bookCommandMemberService;

    @DisplayName("도서 리뷰 등록 테스트")
    @Test
    void testWriteReview() {

        Long bookId = 18L;
        Long memberUid = 7L;

        ReviewRequest request = ReviewRequest.builder()
                .bookId(bookId)
                .memberUid(memberUid)
                .content("역사란 무엇인가에 대해 알 수 있었습니다!")
                .rating(4)
                .build();

        ReviewResponse response = bookCommandMemberService.writeReview(request);

        assertThat(response).isNotNull();
        assertThat(response.bookId()).isEqualTo(bookId);
        assertThat(response.memberUid()).isEqualTo(memberUid);
        assertThat(response.content()).isEqualTo("역사란 무엇인가에 대해 알 수 있었습니다!");
        assertThat(response.rating()).isEqualTo(4);
    }

    @DisplayName("도서 리뷰 수정 테스트")
    @Test
    void testModifyReview() {

        Long reviewId = 1L;
        Long bookId = 19L;
        Long memberUid = 2L;

        ReviewRequest request = ReviewRequest.builder()
                .bookId(bookId)
                .memberUid(memberUid)
                .content("문장이 너무 아름다웠어요!")
                .rating(5)
                .build();

        ReviewResponse response = bookCommandMemberService.modifyReview(request, reviewId);

        assertThat(response).isNotNull();
        assertThat(response.bookId()).isEqualTo(bookId);
        assertThat(response.memberUid()).isEqualTo(memberUid);
        assertThat(response.content()).isEqualTo("문장이 너무 아름다웠어요!");
        assertThat(response.rating()).isEqualTo(5);
    }

    @DisplayName("존재하지 않는 도서 리뷰 수정 시 예외발생 테스트")
    @Test
    void testModifyNotExistReviewException() {

        Long reviewId = 1234456L;
        Long bookId = 19L;
        Long memberUid = 2L;

        ReviewRequest request = ReviewRequest.builder()
                .bookId(bookId)
                .memberUid(memberUid)
                .content("예외 발생")
                .rating(5)
                .build();

        assertThatThrownBy(() -> bookCommandMemberService.modifyReview(request, reviewId))
                .isInstanceOf(ReviewNotFoundException.class)
                .hasMessage("존재하지 않는 리뷰 입니다.");
    }

    @DisplayName("도서 리뷰 수정 시 잘못된 정보 입력 테스트")
    @Test
    void testModifyReviewException() {

        Long reviewId = 1L;
        Long bookId = 19L;
        Long memberUid = 2L;

        ReviewRequest request1 = ReviewRequest.builder()
                .bookId(1102L)
                .memberUid(memberUid)
                .content("도서 불일치!")
                .rating(5)
                .build();

        assertThatThrownBy(() -> bookCommandMemberService.modifyReview(request1, reviewId))
                .isInstanceOf(InconsistentReviewException.class)
                .hasMessage("리뷰 도서가 일치하지 않습니다.");

        ReviewRequest request2 = ReviewRequest.builder()
                .bookId(bookId)
                .memberUid(20L)
                .content("작성자 불일치!")
                .rating(5)
                .build();

        assertThatThrownBy(() -> bookCommandMemberService.modifyReview(request2, reviewId))
                .isInstanceOf(InconsistentReviewException.class)
                .hasMessage("리뷰 작성자가 일치하지 않습니다.");
    }

    @DisplayName("도서 리뷰 삭제 테스트")
    @Test
    void testDeleteReview() {

        Long reviewId = 1L;

        assertDoesNotThrow(() -> bookCommandMemberService.deleteReview(reviewId));

    }

    @DisplayName("존재하지 않는 도서 리뷰 삭제 시 예외 발생 테스트")
    @Test
    void testDeleteNotExistReview() {

        Long reviewId = 1234567L;

        assertThatThrownBy(() -> bookCommandMemberService.deleteReview(reviewId))
                .isInstanceOf(ReviewNotFoundException.class)
                .hasMessage("존재하지 않는 리뷰 입니다.");

    }

}
