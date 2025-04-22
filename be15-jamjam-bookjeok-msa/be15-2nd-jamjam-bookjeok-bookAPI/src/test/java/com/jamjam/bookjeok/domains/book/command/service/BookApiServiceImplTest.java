package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.BookApiDTO;
import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.entity.BookCategory;
import com.jamjam.bookjeok.domains.book.command.entity.Publisher;
import com.jamjam.bookjeok.domains.book.query.dto.BookDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class BookApiServiceImplTest {

    @Autowired
    private BookApiService bookApiService;

    @DisplayName("ISBN을 통한 신규 도서 등록")
    @Test
    void testNewBookRegist() {

        String isbn = "9788936439750";

        BookApiDTO apiBook = bookApiService.getBookByIsbn(isbn);

        assertThat(apiBook).isNotNull();
        assertThat(apiBook.getIsbn()).isEqualTo(isbn);

    }

    @DisplayName("카테고리 이름으로 카테고리 정보 가져오기 테스트")
    @Test
    void testFindCategoryByCategoryName() {

        String categoryName = "한국소설";
        BookCategory bookCategory = bookApiService.findCategoryByCategoryName(categoryName);

        assertThat(bookCategory).isNotNull();

    }

    @DisplayName("출판사 이름으로 출판사 정보 가져오기 테스트")
    @Test
    void testFindPublisherByName() {

        String publisherName = "민음사";
        Publisher publisher = bookApiService.findPublisher(publisherName);

        assertThat(publisher).isNotNull();

    }

    @DisplayName("작가 정보 저장 테스트")
    @Test
    void registAuthorsByOpenAPI() {

        String[] authorArr = new String[]{"김영하", "다카노 카즈아키", "천쓰홍", "록산 게이"};

        bookApiService.registAuthor(authorArr, 1L);

        assertDoesNotThrow(() -> { return HttpStatus.CREATED;});

    }

    @DisplayName("도서 저자 정보 저장 테스트")
    @Test
    void registBookAuthors() {

        bookApiService.registBookAuthor(1L, 1L);

        assertDoesNotThrow(() -> { return HttpStatus.CREATED;});

    }
}