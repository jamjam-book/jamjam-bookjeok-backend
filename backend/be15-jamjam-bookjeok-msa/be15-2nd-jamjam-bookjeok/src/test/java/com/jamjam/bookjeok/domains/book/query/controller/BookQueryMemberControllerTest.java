package com.jamjam.bookjeok.domains.book.query.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookQueryMemberControllerTest {

    @Autowired
    private MockMvc mvc;

    private static final String BASE_URL = "/api/v1";

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("도서 조회 테스트")
    @Test
    void testSelectBook() throws Exception {
        mvc.perform(get(BASE_URL + "/book/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", "0")
                                .param("size", "10")
                                .param("sort", "latest")
                                .param("minPrice", "0")
                                .param("maxPrice", "50000")
                                .param("categoryIds", "2")
                        // 카테고리를 여러 개 넘길 경우 아래처럼 param()을 여러 번 호출
                        // .param("categoryIds", "1")
                        // .param("categoryIds", "2")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("bookId")));
    }

    @DisplayName("도서 상세 조회 테스트")
    @Test
    void testSelectBookDetail() throws Exception{

        Long bookId = 1L;

        mvc.perform(get(BASE_URL+ "/book/{bookId}", bookId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("일주일 간 판매량 top 10위 조회")
    @Test
    void testSelectBookOrderByOrdersWeekly() throws Exception{

        mvc.perform(get(BASE_URL + "/book/popular")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

    }

    @DisplayName("작가별 도서 조회 테스트")
    @Test
    void testSelectAuthorBooks() throws Exception{

        Long authorId = 7L;

        mvc.perform(get(BASE_URL + "/book/author/{authorId}",authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("bookId")));

    }

}
