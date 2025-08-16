package com.jamjam.bookjeok.domains.member.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.member.command.dto.request.InterestBookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
class InterestBookCommandControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    String baseUrl = "/api/v1/members";

    @DisplayName("관심 도서 등록하기")
    @Test
    void createInterestBookTest() throws Exception {
        String memberId = "user01";
        Long bookId = 5L;
        InterestBookRequest request = new InterestBookRequest(bookId, memberId);

        mockMvc.perform(post(baseUrl + "/interest/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.bookName").value("노르웨이의 숲"))
                .andReturn();
    }

    @DisplayName("관심 도서 삭제하기")
    @Test
    void deleteInterestBookTest() throws Exception {
        String memberId = "user02";
        Long bookId = 2L;

        InterestBookRequest request = new InterestBookRequest(2L, memberId);

        mockMvc.perform(delete(baseUrl + "/{memberId}/interest/books/{bookId}", memberId, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }
}