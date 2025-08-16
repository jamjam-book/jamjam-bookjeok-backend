package com.jamjam.bookjeok.domains.book.query.mapper;

import com.jamjam.bookjeok.domains.book.query.dto.*;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class BookMapperTest {

    @Autowired
    BookMapper bookMapper;

    @DisplayName("기본(최신순) 도서 목록 조회 테스트")
    @Test
    void testFindBookListOrderByDefault() {

        BookSearchCondition condition = new BookSearchCondition();

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("관심 많은 순 도서 목록 조회 테스트")
    @Test
    void testFindBookListOrderByInterestCounts() {

        BookSearchCondition condition = new BookSearchCondition();
        condition.setSort("interest");

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("판매량 많은 순 도서 목록 조회 테스트")
    @Test
    void testFindBookListOrderByOrderCounts() {

        BookSearchCondition condition = new BookSearchCondition();
        condition.setSort("orders");

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("카테고리 별 도서 조회")
    @Test
    void testFindBookListWhereCategory() {

        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(1L);
        BookSearchCondition condition = new BookSearchCondition();
        condition.setCategoryIds(categoryIds);

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("작가 이름으로 도서 조회")
    @Test
    void testFindBookListWhereAuthor() {

        BookSearchCondition condition = new BookSearchCondition();
        condition.setKeywordType("author");
        condition.setKeyword("한강");

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("도서 제목으로 도서 조회")
    @Test
    void testFindBookListWhereBookName() {

        BookSearchCondition condition = new BookSearchCondition();
        condition.setKeywordType("title");
        condition.setKeyword("지구");

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("출판사 별 도서 조회")
    @Test
    void testFindBookListWherePublisher() {

        BookSearchCondition condition = new BookSearchCondition();
        condition.setKeywordType("publisher");
        condition.setKeyword("위즈");

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(condition);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("회원 도서 상세 조회")
    @Test
    void testGetBookDetail() {
        Long bookId = 1L;
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);

        BookDetailPageDTO book = bookMapper.getBookDetail(params);

        assertThat(book).isNotNull();

        System.out.println(book);

    }

    @DisplayName("도서 상세 조회 시 작가 조회")
    @Test
    void testGetAuthors() {
        Long bookId = 1L;
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);

        List<AuthorDTO> authors = bookMapper.getAuthors(params);

        assertThat(authors).isNotNull();

        authors.forEach(System.out::println);

    }

    @DisplayName("도서 상세 조회 시 리뷰 조회")
    @Test
    void testGetReviews() {
        Long bookId = 1L;
        PageRequest pageRequest = new PageRequest(1, 10);
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("pageRequest", pageRequest);

        List<ReviewDTO> reviews = bookMapper.getReviews(params);

        assertThat(reviews).isNotNull();

        reviews.forEach(System.out::println);

    }

    @DisplayName("일주일 간 판매량 TOP10 도서 조회")
    @Test
    void testGetPopularBooks() {

        List<PopularBookDTO> books = bookMapper.getPopularBooks();

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("회원 리뷰 자격 증명 테스트")
    @Test
    void validCheckReviewer() {

        Long bookId = 25L;
        Long memberUid = 7L;
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("memberUid", memberUid);

        Long memberId = bookMapper.validCheckReviewer(params);

        assertThat(memberId).isEqualTo(memberUid);

    }

    @DisplayName("작가 별 도서 조회 테스트")
    @Test
    void testGetAuthorBooks() {

        Long authorId = 7L;
        Map<String, Object> params = new HashMap<>();
        params.put("authorId", authorId);

        List<BookDetailDTO> books = bookMapper.getAuthorBooks(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }
}
