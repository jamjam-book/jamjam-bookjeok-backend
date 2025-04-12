package com.jamjam.bookjeok.domains.book.mapper;

import com.jamjam.bookjeok.domains.book.dto.*;
import com.jamjam.bookjeok.domains.book.repository.mapper.BookMapper;
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

        Map<String, Object> params = new HashMap<>();

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("관심 많은 순 도서 목록 조회 테스트")
    @Test
    void testFindBookListOrderByInterestCounts() {

        Map<String, Object > params = new HashMap<>();
        params.put("array", "interest");

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("판매량 많은 순 도서 목록 조회 테스트")
    @Test
    void testFindBookListOrderByOrderCounts() {

        Map<String, Object > params = new HashMap<>();
        params.put("array", "orders");

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("카테고리 별 도서 조회")
    @Test
    void testFindBookListWhereCategory() {

        Map<String, Object > params = new HashMap<>();
        params.put("option", "category");
        Long categoryId = 1L;
        params.put("search", categoryId);

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("작가 별 도서 조회")
    @Test
    void testFindBookListWhereAuthor() {

        Map<String, Object > params = new HashMap<>();
        params.put("option", "author");
        String authorName = "한강"; // 더미 데이터에 있는 데이터로 확인
        params.put("search", authorName);

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("도서 제목으로 도서 조회")
    @Test
    void testFindBookListWhereBookName() {

        Map<String, Object > params = new HashMap<>();
        params.put("option", "name");
        String name = "지구"; // 더미 데이터에 있는 데이터로 확인
        params.put("search", name);

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("출판사 별 도서 조회")
    @Test
    void testFindBookListWherePublisher() {

        Map<String, Object > params = new HashMap<>();
        params.put("option", "publisher");
        String publisher = "위즈"; // 더미 데이터에 있는 데이터로 확인
        params.put("search", publisher);

        List<BookDetailDTO> books  = bookMapper.findBookListOrderByOption(params);

        assertThat(books).isNotNull();

        books.forEach(System.out::println);

    }

    @DisplayName("카테고리 목록 조회")
    @Test
    void testFindAllCategory() {

        List<BookCategoryDTO> categories = bookMapper.findAllCategory();

        assertThat(categories).isNotNull();

        categories.forEach(System.out::println);

    }

    @DisplayName("회원 도서 상세 조회")
    @Test
    void testGetBookDetail() {
        String isbn = "9781082502224";
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", isbn);

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
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);

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
}
