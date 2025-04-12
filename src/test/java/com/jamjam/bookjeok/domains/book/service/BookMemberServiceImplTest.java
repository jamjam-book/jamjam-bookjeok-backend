package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDetailPageDTO;
import com.jamjam.bookjeok.domains.book.dto.PopularBookDTO;
import com.jamjam.bookjeok.domains.book.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.dto.response.ReviewResponse;
import com.jamjam.bookjeok.exception.book.review.InconsistentReviewException;
import com.jamjam.bookjeok.exception.book.review.ReviewNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class BookMemberServiceImplTest {

    @Autowired
    BookMemberService bookMemberService;

    @DisplayName("전체 도서 조회 테스트")
    @Test
    void testGetBookList() {

        Map<String, Object> params = new HashMap<>();

        List<BookDetailDTO> books = bookMemberService.getBookList(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 판매량순 조회 테스트")
    @Test
    void testGetBookListOrderByOrders() {

        Map<String, Object> params = new HashMap<>();
        params.put("array", "orders");

        List<BookDetailDTO> books = bookMemberService.getBookList(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 관심순 조회 테스트")
    @Test
    void testGetBookListOrderByLikes() {
        Map<String, Object> params = new HashMap<>();
        params.put("array", "interest");

        List<BookDetailDTO> books = bookMemberService.getBookList(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 상세 조회 테스트")
    @Test
    void testGetBookDetail() {
        String isbn = "9781082502224";
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", isbn);

        BookDetailPageDTO book = bookMemberService.getBookDetail(params);

        assertThat(book).isNotNull();

        System.out.println(book);
    }

    @DisplayName("일주일 간 판매량 TOP10 도서 조회")
    @Test
    void testGetPopularBooks() {

        List<PopularBookDTO> books = bookMemberService.getPopularBooks();

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

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

        ReviewResponse response = bookMemberService.writeReview(request);

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

        ReviewResponse response = bookMemberService.modifyReview(request, reviewId);

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

        assertThatThrownBy(() -> bookMemberService.modifyReview(request, reviewId))
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

        assertThatThrownBy(() -> bookMemberService.modifyReview(request1, reviewId))
                .isInstanceOf(InconsistentReviewException.class)
                .hasMessage("리뷰 도서가 일치하지 않습니다.");

        ReviewRequest request2 = ReviewRequest.builder()
                .bookId(bookId)
                .memberUid(20L)
                .content("작성자 불일치!")
                .rating(5)
                .build();

        assertThatThrownBy(() -> bookMemberService.modifyReview(request2, reviewId))
                .isInstanceOf(InconsistentReviewException.class)
                .hasMessage("리뷰 작성자가 일치하지 않습니다.");
    }

    @DisplayName("도서 리뷰 삭제 테스트")
    @Test
    void testDeleteReview() {

        Long reviewId = 1L;

        assertDoesNotThrow(() -> bookMemberService.deleteReview(reviewId));

    }

    @DisplayName("존재하지 않는 도서 리뷰 삭제 시 예외 발생 테스트")
    @Test
    void testDeleteNotExistReview() {

        Long reviewId = 1234567L;

        assertThatThrownBy(() -> bookMemberService.deleteReview(reviewId))
                .isInstanceOf(ReviewNotFoundException.class)
                .hasMessage("존재하지 않는 리뷰 입니다.");

    }

    @DisplayName("회원 리뷰 자격 증명 테스트")
    @Test
    void validCheckReviewer() {

        Long bookId = 25L;
        Long memberUid = 7L;

        ReviewRequest request = ReviewRequest.builder()
                .bookId(bookId)
                .memberUid(memberUid)
                .content("리뷰 자격 증명 완료!")
                .rating(5)
                .build();

        boolean isBuyer = bookMemberService.validCheckBuyer(request);

        assertThat(isBuyer).isEqualTo(true);

    }

}
