package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.query.dto.*;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
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

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class BookQueryMemberServiceImplTest {

    @Autowired
    BookQueryMemberService bookQueryMemberService;

    @DisplayName("전체 도서 조회 테스트")
    @Test
    void testGetBookList() {

        BookSearchCondition condition = new BookSearchCondition();

        List<BookDetailDTO> books = bookQueryMemberService.getBookList(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 판매량순 조회 테스트")
    @Test
    void testGetBookListOrderByOrders() {

        BookSearchCondition condition = new BookSearchCondition();
        condition.setSort("orders");

        List<BookDetailDTO> books = bookQueryMemberService.getBookList(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 관심순 조회 테스트")
    @Test
    void testGetBookListOrderByLikes() {

        BookSearchCondition condition = new BookSearchCondition();
        condition.setSort("interest");

        List<BookDetailDTO> books = bookQueryMemberService.getBookList(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 상세 조회 테스트")
    @Test
    void testGetBookDetail() {
        Long bookId = 1L;
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);

        BookDetailPageDTO book = bookQueryMemberService.getBookDetail(params);

        assertThat(book).isNotNull();

        System.out.println(book);
    }

    @DisplayName("도서 리뷰 조회 테스트")
    @Test
    void testGetBookReviews() {
        Long bookId = 32L;
        PageRequest pageRequest = new PageRequest(1, 10);

        ReviewListDTO reviews = bookQueryMemberService.getBookReviews(bookId, pageRequest);

        assertThat(reviews).isNotNull();

        reviews.getReviews().forEach(System.out::println);

    }

    @DisplayName("일주일 간 판매량 TOP10 도서 조회")
    @Test
    void testGetPopularBooks() {

        List<PopularBookDTO> books = bookQueryMemberService.getPopularBooks();

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

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

        boolean isBuyer = bookQueryMemberService.validCheckBuyer(request);

        assertThat(isBuyer).isEqualTo(true);

    }

    @DisplayName("작가별 도서 조회 테스트")
    @Test
    void testGetAuthorBooks() {

        Long authorId = 7L;
        Map<String, Object> params = new HashMap<>();
        params.put("authorId", authorId);

        List<BookDetailDTO> books = bookQueryMemberService.getAuthorBookList(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

}
