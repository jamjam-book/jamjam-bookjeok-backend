package com.jamjam.bookjeok.domains.book.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookApiControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private static final String BASE_URL = "/api/v1";

    @DisplayName("도서 정보 insert를 위한 API 호출 테스트")
    @Test
    void registBook() throws Exception {

        // given
        String keyword = "김초엽";

        // when
        String body = mapper.writeValueAsString(keyword);

        // then
        mvc.perform(post(BASE_URL + "/bookApi/regist")
                .param("keyword", keyword)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isCreated());

    }
}