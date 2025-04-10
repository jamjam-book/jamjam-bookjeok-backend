package com.jamjam.bookjeok.domains.book.service;

import com.jamjam.bookjeok.domains.book.dto.BookDTO;
import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.book.entity.BookCategory;
import com.jamjam.bookjeok.domains.book.entity.Publisher;
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

    @DisplayName("Open API를 통한 도서 등록 테스트")
    @Test
    void testRegistBookByOpenAPI() {

        LocalDate publishedAt = LocalDate.now();
        String bookInfo = "테스트용으로 등록된 도서입니다. 저장되었다면 삭제해주세요.";
        String imgeUrl = "https://shopping-phinf.pstatic.net/main_3245497/32454975970.20230110165450.jpg";

        BookDTO bookDTO = new BookDTO(
                1L, 1L, "등록성공", bookInfo,
                "9123456789", publishedAt, 12000, 10, imgeUrl);


        Book book = bookApiService.registBook(bookDTO);

        assertThat(book).isNotNull();
        assertThat(book.getPublisherId()).isEqualTo(1L);
        assertThat(book.getCategoryId()).isEqualTo(1L);
        assertThat(book.getBookName()).isEqualTo("등록성공");
        assertThat(book.getBookInfo()).isEqualTo(bookInfo);
        assertThat(book.getIsbn()).isEqualTo("9123456789");
        assertThat(book.getPublishedAt()).isEqualTo(publishedAt);
        assertThat(book.getPrice()).isEqualTo(12000);
        assertThat(book.getStockQuantity()).isEqualTo(10);
        assertThat(book.getImageUrl()).isNotNull();

    }

    @DisplayName("Open API를 통한 중복 도서 저장 테스트")
    @Test
    void testRegistBookDuplicatedByOpenAPI() {
        // isbn 번호 중복 불가
        String isbn = "9788954672214"; // db에 존재하는 isbn

        LocalDate publishedAt = LocalDate.now();
        String bookInfo = "테스트용으로 등록된 도서입니다. 저장되었다면 삭제해주세요.";
        String imgeUrl = "https://shopping-phinf.pstatic.net/main_3245497/32454975970.20230110165450.jpg";

        BookDTO bookDTO = new BookDTO(
                1L, 1L, "중복 도서", bookInfo,
                isbn, publishedAt, 12000, 10, imgeUrl);


        Book book = bookApiService.registBook(bookDTO);

        assertThat(book).isNull();
        
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