package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.ReviewRequest;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailPageDTO;
import com.jamjam.bookjeok.domains.book.query.dto.PopularBookDTO;
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

        Map<String, Object> params = new HashMap<>();

        List<BookDetailDTO> books = bookQueryMemberService.getBookList(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 판매량순 조회 테스트")
    @Test
    void testGetBookListOrderByOrders() {

        Map<String, Object> params = new HashMap<>();
        params.put("array", "orders");

        List<BookDetailDTO> books = bookQueryMemberService.getBookList(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 관심순 조회 테스트")
    @Test
    void testGetBookListOrderByLikes() {
        Map<String, Object> params = new HashMap<>();
        params.put("array", "interest");

        List<BookDetailDTO> books = bookQueryMemberService.getBookList(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);
    }

    @DisplayName("도서 상세 조회 테스트")
    @Test
    void testGetBookDetail() {
        String isbn = "9781082502224";
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", isbn);

        BookDetailPageDTO book = bookQueryMemberService.getBookDetail(params);

        assertThat(book).isNotNull();

        System.out.println(book);
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

}
