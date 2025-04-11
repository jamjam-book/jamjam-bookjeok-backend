package com.jamjam.bookjeok.domains.book.mapper;

import com.jamjam.bookjeok.domains.book.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.dto.BookDetailDTO;
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
        String authorName = "유시민"; // 더미 데이터에 있는 데이터로 확인
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
        String publisher = "민음"; // 더미 데이터에 있는 데이터로 확인
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
}
