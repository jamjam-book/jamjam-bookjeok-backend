package com.jamjam.bookjeok.domains.book.query.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookQueryAdminQueryControllerTest {

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

    @DisplayName("도서 조회 테스트")
    @Test
    void testSelectBook() throws Exception{

        Map<String, Object> params = new HashMap<>();

        mvc.perform(get(BASE_URL + "/book/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("params", objectMapper.writeValueAsString(params)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("bookId")));

    }

    @DisplayName("도서 상세 조회 테스트")
    @Test
    void testSelectBookDetail() throws Exception{

        //DB에 존재하는 isbn 번호 사용
        String isbn = "9781082502224";

        mvc.perform(get(BASE_URL+ "/book/{isbn}", isbn))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @DisplayName("카테고리 조회 테스트")
    @Test
    void testFindCategories() throws Exception{

        mvc.perform(get(BASE_URL + "/book/ca/list"))
                .andExpect(status().isOk())
                .andDo(print());

    }

}