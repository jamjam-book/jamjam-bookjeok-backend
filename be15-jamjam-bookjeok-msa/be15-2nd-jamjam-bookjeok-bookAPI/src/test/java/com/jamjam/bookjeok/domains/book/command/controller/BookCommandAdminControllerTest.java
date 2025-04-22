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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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

    @DisplayName("새로운 도서 등록 테스트")
    @Test
    void testGetNewBookInfo() throws Exception {
        String isbn = "9788936439750";

        mvc.perform(post(BASE_URL + "/book/check/{isbn}",isbn)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.book.isbn").value(isbn));
    }

    @DisplayName("존재하는 도서 ID 반환 테스트")
    @Test
    void testGetExistBookInfo() throws Exception {

        String isbn = "9781082502224";

        mvc.perform(post(BASE_URL + "/book/check/{isbn}",isbn)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.bookId").exists());
    }

    @DisplayName("도서 수량 수정 테스트")
    @Test
    void testModifyBook() throws Exception{

        Long bookId = 1L;
        int quantity = 10;

        mvc.perform(put(BASE_URL + "/book/mod/{bookId}", bookId)
                        .param("quantity",String.valueOf(quantity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.stockQuantity").value(quantity));

    }

    @DisplayName("존재하지 않는 도서 수정 요청 시 예외 발생 테스트")
    @Test
    void testModifyNotExistBook() throws Exception{

        Long bookId = 123456789L;
        int quantity = 10;

        mvc.perform(put(BASE_URL + "/book/mod/{bookId}", bookId)
                        .param("quantity",String.valueOf(quantity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("존재하지 않는 도서입니다."))
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