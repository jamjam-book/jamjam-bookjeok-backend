package com.jamjam.bookjeok.domains.book.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryModifyRequest;
import com.jamjam.bookjeok.domains.book.command.dto.request.BookCategoryRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookCommandAdminControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private static final String BASE_URL = "/api/v1/admin";
    private HttpHeaders headers;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

   /* @DisplayName("도서 등록 테스트")
    @Test
    void testRegistBook() throws Exception{

        MockMultipartFile imageFile = new MockMultipartFile(
                "bookImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "bookRequest", "request", "application/json",
                """
                {
                  "publisherId": 1,
                  "categoryId": 1,
                  "bookName": "등록성공",
                  "isbn": "9999999999999",
                  "publishedAt": "2024-12-25",
                  "price": 12000,
                  "stockQuantity": 10,
                  "bookInfo": "테스트용으로 등록된 도서입니다."
                }
                """.getBytes()
        );

        mvc.perform(multipart(BASE_URL + "/book/in")
                        .file(imageFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.publisherId").value(1L))
                .andExpect(jsonPath("$.data.categoryId").value(1L))
                .andExpect(jsonPath("$.data.bookName").value("등록성공"))
                .andExpect(jsonPath("$.data.isbn").value("9999999999999"))
                .andExpect(jsonPath("$.data.publishedAt").exists())
                .andExpect(jsonPath("$.data.price").value(12000))
                .andExpect(jsonPath("$.data.stockQuantity").value(10))
                .andExpect(jsonPath("$.data.bookInfo").value("테스트용으로 등록된 도서입니다."))
                .andExpect(jsonPath("$.data.imageUrl").exists());

    }
*/
   /* @DisplayName("중복 도서 등록 시 예외 발생 테스트")
    @Test
    void testRegistDuplicatedBookException() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile(
                "bookImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "bookRequest", "request", "application/json",
                """
                {
                  "publisherId": 1,
                  "categoryId": 1,
                  "bookName": "중복도서",
                  "isbn": "9781082502224",
                  "publishedAt": "%s",
                  "price": 12000,
                  "stockQuantity": 10,
                  "bookInfo": "중복도서입니다."
                }
                """.formatted(LocalDate.now()).getBytes()
        );

        mvc.perform(multipart(BASE_URL + "/book/in")
                        .file(imageFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("이미 등록된 도서입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }*/

   /* @DisplayName("도서 등록 시 필수 입력 값 누락 시 예외 발생 테스트")
    @Test
    void testRegistNotNUllElementException() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile(
                "bookImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "bookRequest", "request", "application/json",
                """
                {
                  "publisherId": 1,
                  "categoryId": 1,
                  "bookName": "예외발생",
                  "isbn": "",
                  "publishedAt": "%s",
                  "price": 12000,
                  "stockQuantity": 10,
                  "bookInfo": "등록될 수 없습니다."
                }
                """.formatted(LocalDate.now()).getBytes()
        );

        mvc.perform(multipart(HttpMethod.POST, BASE_URL + "/book/in")
                        .file(imageFile)
                        .file(jsonFile).headers(headers)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("isbn 번호는 비어있을 수 없습니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }
*/
    @DisplayName("도서 수정 테스트")
    @Test
    void testModifyBook() throws Exception{

        MockMultipartFile imageFile = new MockMultipartFile(
                "bookImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "bookRequest", "request", "application/json",
                """
                {
                  "publisherId": 1,
                  "categoryId": 1,
                  "bookName": "수정성공",
                  "isbn": "9781082502224",
                  "publishedAt": "2024-12-25",
                  "price": 12000,
                  "stockQuantity": 11,
                  "bookInfo": "수정된 도서입니다."
                }
                """.getBytes()
        );

        mvc.perform(multipart(HttpMethod.POST, BASE_URL + "/book/mod")
                        .file(imageFile)
                        .file(jsonFile).headers(headers)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.publisherId").value(1L))
                .andExpect(jsonPath("$.data.categoryId").value(1L))
                .andExpect(jsonPath("$.data.bookName").value("수정성공"))
                .andExpect(jsonPath("$.data.isbn").value("9781082502224"))
                .andExpect(jsonPath("$.data.publishedAt").value("2024-12-25"))
                .andExpect(jsonPath("$.data.price").value(12000))
                .andExpect(jsonPath("$.data.stockQuantity").value(11))
                .andExpect(jsonPath("$.data.bookInfo").value("수정된 도서입니다."))
                .andExpect(jsonPath("$.data.imageUrl").exists());

    }

    @DisplayName("도서 수량 수정 테스트")
    @Test
    void testModifyBookStockQuantity() throws Exception{

        mvc.perform(post(BASE_URL + "/book/mod/q")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bookId", "1")
                        .param("quantity", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.stockQuantity").value(50));
    }

    @DisplayName("존재하지 않는 도서 수정 요청 시 예외 발생 테스트")
    @Test
    void testModifyNotExistBook() throws Exception{

        MockMultipartFile imageFile = new MockMultipartFile(
                "bookImg", "cover.png", "image/png", "fake image data".getBytes());

        MockMultipartFile jsonFile = new MockMultipartFile(
                "bookRequest", "request", "application/json",
                """
                {
                  "publisherId": 1,
                  "categoryId": 1,
                  "bookName": "수정 실패",
                  "isbn": "1234567891012",
                  "publishedAt": "%s",
                  "price": 12000,
                  "stockQuantity": 11,
                  "bookInfo": "예외 발생"
                }
                """.formatted(LocalDate.now()).getBytes()
        );

        mvc.perform(multipart(HttpMethod.POST, BASE_URL + "/book/mod")
                        .file(imageFile)
                        .file(jsonFile).headers(headers)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("존재하지 않는 ISBN 입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }


    @DisplayName("도서 삭제 테스트")
    @Test
    void testDeleteBook() throws Exception{

        mvc.perform(delete(BASE_URL + "/book/del")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "1")
        ).andExpect(status().isOk());

    }

    @DisplayName("존재하지 않는 도서 삭제 요청 시 예외 발생")
    @Test
    void testDeleteNotExistBook() throws Exception{

        mvc.perform(delete(BASE_URL + "/book/del")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bookId", "123456789"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("존재하지 않는 도서입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }

    @DisplayName("카테고리 등록 테스트")
    @Test
    void testRegistCategory() throws Exception{

        BookCategoryRequest request = new BookCategoryRequest("카테고리");

        String content = mapper.writeValueAsString(request);

        mvc.perform(post(BASE_URL + "/book/ca/in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.categoryName").value("카테고리"));

    }

    @DisplayName("카테고리 등록 중복 테스트")
    @Test
    void testRegistDuplicatedCategory() throws Exception{

        BookCategoryRequest request = new BookCategoryRequest("소설");

        String content = mapper.writeValueAsString(request);

        mvc.perform(post(BASE_URL + "/book/ca/in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("이미 존재하는 카테고리 입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }

    @DisplayName("카테고리 수정 테스트")
    @Test
    void testModifyCategory() throws Exception{

        BookCategoryModifyRequest request = new BookCategoryModifyRequest("소설", "통과");

        String content = mapper.writeValueAsString(request);

        mvc.perform(post(BASE_URL + "/book/ca/mod")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.categoryName").value("통과"));

    }

    @DisplayName("중복된 카테고리로 수정 시 예외 발생 테스트")
    @Test
    void testModifyDuplicatedCategory() throws Exception{

        BookCategoryModifyRequest request = new BookCategoryModifyRequest("소설", "에세이");

        String content = mapper.writeValueAsString(request);

        mvc.perform(post(BASE_URL + "/book/ca/mod")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("이미 존재하는 카테고리 입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }

    @DisplayName("존재하지 않는 카테고리 수정 시 예외 발생 테스트")
    @Test
    void testModifyNotExistCategory() throws Exception{

        BookCategoryModifyRequest request = new BookCategoryModifyRequest("실패", "신규카테고리");

        String content = mapper.writeValueAsString(request);

        mvc.perform(post(BASE_URL + "/book/ca/mod")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("존재하지 않는 카테고리 입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }

    @DisplayName("카테고리 삭제 테스트")
    @Test
    void testDeleteCategory() throws Exception{

        BookCategoryRequest request = new BookCategoryRequest("소설");

        String content = mapper.writeValueAsString(request);

        mvc.perform(delete(BASE_URL + "/book/ca/del")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk());
    }

    @DisplayName("존재하지 않는 카테고리 삭제 예외 발생 테스트")
    @Test
    void testDeleteNotExistCategory() throws Exception {

        BookCategoryRequest request = new BookCategoryRequest("무존재");

        String content = mapper.writeValueAsString(request);

        mvc.perform(delete(BASE_URL + "/book/ca/del")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("존재하지 않는 카테고리 입니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

    }

}