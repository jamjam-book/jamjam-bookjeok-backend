package com.jamjam.bookjeok.domains.book.command.service;

import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookRequest;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookCategoryResponse;
import com.jamjam.bookjeok.domains.book.command.dto.response.BookResponse;

import com.jamjam.bookjeok.exception.book.BookNotFoundException;
import com.jamjam.bookjeok.exception.book.RegistPreexistingBookException;
import com.jamjam.bookjeok.exception.book.category.BookCategoryNotFoundException;
import com.jamjam.bookjeok.exception.book.category.RegistPreexistingCategoryException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class BookCommandAdminServiceImplTest {

    @Autowired
    BookCommandAdminService bookCommandAdminService;

    @DisplayName("관리자 도서 등록 및 중복 저장 예외 발생 테스트")
    @Test
    void testRegistBook() throws IOException {

        LocalDate publishedAt = LocalDate.now();
        String bookInfo = "테스트용으로 등록된 도서입니다. 저장되었다면 삭제해주세요.";

        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                "test data".getBytes());

        BookRequest request = new BookRequest(1L, 1L, "등록성공", "9123456789", publishedAt, 12000, 10, bookInfo);

        BookResponse response1 = bookCommandAdminService.registBook(request, file);

        log.info("{}", response1.toString());

        assertThat(response1).isNotNull();

        assertThat(response1.publisherId()).isEqualTo(1L);
        assertThat(response1.categoryId()).isEqualTo(1L);
        assertThat(response1.bookName()).isEqualTo("등록성공");
        assertThat(response1.isbn()).isEqualTo("9123456789");
        assertThat(response1.publishedAt()).isEqualTo(publishedAt);
        assertThat(response1.price()).isEqualTo(12000);
        assertThat(response1.stockQuantity()).isEqualTo(10);
        assertThat(response1.imageUrl()).isNotNull();
        assertThat(response1.bookInfo()).isEqualTo(bookInfo);

        assertThatThrownBy(() -> bookCommandAdminService.registBook(request, file))
                .isInstanceOf(RegistPreexistingBookException.class)
                .hasMessage("이미 등록된 도서입니다.");


    }

    @DisplayName("관리자 도서 수정 테스트 ")
    @Test
    void testModifyBook() {

        LocalDate publishedAt = LocalDate.now();
        String bookInfo = "테스트용으로 수정된 도서입니다. 저장되었다면 삭제해주세요.";

        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                "test data".getBytes());

        BookRequest request = new BookRequest(1L, 1L, "수정성공", "9782311676709", publishedAt, 1000, 3, bookInfo);

        assertDoesNotThrow(() ->  bookCommandAdminService.modifyBook(request, file));

    }

    @DisplayName("관리자가 존재하지 않는 도서의 수정을 요청했을 때 예외 발생 테스트")
    @Test
    void testModifyBookThrowException() {

        LocalDate publishedAt = LocalDate.now();
        String bookInfo = "테스트용으로 수정된 도서입니다. 저장되었다면 삭제해주세요.";

        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                "test data".getBytes());

        BookRequest request = new BookRequest(1L, 1L, "존재하지 않는 도서", "123456789", publishedAt, 1000, 3, bookInfo);

        assertThatThrownBy(() -> bookCommandAdminService.modifyBook(request, file))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("존재하지 않는 ISBN 입니다.");

    }

    @DisplayName("관리자 도서 삭제 테스트")
    @Test
    void testDeleteBookByBookId() {

        Long bookId = 1L;

        assertDoesNotThrow(() -> bookCommandAdminService.deleteBook(bookId));
        
    }

    @DisplayName("존재하지 않는 도서 삭제시 예외 발생 테스트")
    @Test
    void testDeleteBookByBookIdException() {

        Long bookId = 1216579L;

        assertThatThrownBy(() -> bookCommandAdminService.deleteBook(bookId))
               .isInstanceOf(BookNotFoundException.class)
               .hasMessage("존재하지 않는 도서입니다.");

    }

    @DisplayName("관리자 카테고리 등록 테스트")
    @Test
    void testRegistCategory() {

        String categoryName = "아동";

        BookCategoryRequest request = BookCategoryRequest.builder()
                .categoryName(categoryName)
                .build();

        BookCategoryResponse response = bookCommandAdminService.registCategory(request);

        assertThat(response).isNotNull();
        assertThat(response.categoryName()).isEqualTo(categoryName);

    }

    @DisplayName("관리자 중복된 카테고리 등록 시 예외 발생 테스트")
    @Test
    void testRegistDuplicatedCategoryException() {

        String categoryName = "소설";

        BookCategoryRequest request = BookCategoryRequest.builder()
                .categoryName(categoryName)
                .build();

        assertThatThrownBy(() -> bookCommandAdminService.registCategory(request))
                .isInstanceOf(RegistPreexistingCategoryException.class)
                .hasMessage("이미 존재하는 카테고리 입니다.");

    }

    @DisplayName("관리자 카테고리 수정 테스트")
    @Test
    void testModifyCategory() {

        String categoryName = "소설";
        String newCategoryName = "newName";

        BookCategoryModifyRequest request = BookCategoryModifyRequest.builder()
                .categoryName(categoryName)
                .newCategoryName(newCategoryName)
                .build();

        assertDoesNotThrow(() ->  bookCommandAdminService.modifyCategory(request));

    }

    @DisplayName("관리자가 존재하지 않는 카테고리를 수정할 시 예외 발생 테스트")
    @Test
    void testModifyNotFoundCategory() {

        String categoryName = "NotFound";
        String newCategoryName = "newName";

        BookCategoryModifyRequest request = BookCategoryModifyRequest.builder()
                .categoryName(categoryName)
                .newCategoryName(newCategoryName)
                .build();

        assertThatThrownBy(() -> bookCommandAdminService.modifyCategory(request))
                .isInstanceOf(BookCategoryNotFoundException.class)
                .hasMessage("존재하지 않는 카테고리 입니다.");
    }

    @DisplayName("관리자 카테고리 삭제 테스트")
    @Test
    void testDeleteCategory() {

        String categoryName = "소설";

        BookCategoryRequest request = BookCategoryRequest.builder().categoryName(categoryName).build();

        assertDoesNotThrow(() -> bookCommandAdminService.deleteCategory(request));
    }

    @DisplayName("관리자가 존재하지 않는 카테고리를 삭제할 시 예외 발생 테스트")
    @Test
    void testDeleteNotFoundCategory() {

        String categoryName = "NotFound";

        BookCategoryRequest request = BookCategoryRequest.builder().categoryName(categoryName).build();

        assertThatThrownBy(() -> bookCommandAdminService.deleteCategory(request))
                .isInstanceOf(BookCategoryNotFoundException.class)
                .hasMessage("존재하지 않는 카테고리 입니다.");
    }

}