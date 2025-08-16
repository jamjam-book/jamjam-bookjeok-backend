package com.jamjam.bookjeok.domains.book.query.service;

import com.jamjam.bookjeok.domains.book.query.dto.BookCategoryDTO;
import com.jamjam.bookjeok.domains.book.query.dto.BookDetailDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class BookQueryAdminServiceImplTest {

    @Autowired
    BookQueryAdminService bookQueryAdminService;

    @DisplayName("관리자 전체 도서 조회 (페이징 포함)")
    @Test
    void testFindBookListWithPaging() {
        Map<String, Object> params = new HashMap<>();
        PageRequest pageRequest = PageRequest.of(0, 10); // 첫 페이지, 10개씩

        Page<BookDetailDTO> bookPage = bookQueryAdminService.findBookListOrderByOption(params, pageRequest);

        assertThat(bookPage).isNotNull();
        assertThat(bookPage.getContent()).isNotEmpty();

        bookPage.getContent().forEach(System.out::println);
    }

    @DisplayName("관리자 도서 판매량순 조회 테스트")
    @Test
    void testFindBookListOrderByOrders() {
        Map<String, Object> params = new HashMap<>();
        params.put("sort", "orders");

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BookDetailDTO> bookPage = bookQueryAdminService.findBookListOrderByOption(params, pageRequest);

        assertThat(bookPage).isNotNull();
        assertThat(bookPage.getContent()).isNotEmpty();

        bookPage.getContent().forEach(System.out::println);
    }

    @DisplayName("관리자 도서 관심순 조회 테스트")
    @Test
    void testFindBookListOrderByInterest() {
        Map<String, Object> params = new HashMap<>();
        params.put("sort", "interest");

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BookDetailDTO> bookPage = bookQueryAdminService.findBookListOrderByOption(params, pageRequest);

        assertThat(bookPage).isNotNull();
        assertThat(bookPage.getContent()).isNotEmpty();

        bookPage.getContent().forEach(System.out::println);
    }


    @DisplayName("관리자 전체 카테고리 조회 테스트")
    @Test
    void testFindAllBookCategory() {

        List<BookCategoryDTO> categories = bookQueryAdminService.findBookCategory();

        assertThat(categories).isNotNull();

        categories.forEach(System.out::println);

    }

}