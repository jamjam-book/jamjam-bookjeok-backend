package com.jamjam.bookjeok.domains.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.member.dto.request.InterestAuthorRequest;
import com.jamjam.bookjeok.domains.member.dto.request.InterestBookRequest;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
class InterestBookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("멤버의 아이디로 책 즐겨찾기 목록 가져오기 테스트")
    @Test
    void getInterestBooListByMemberIdTest() throws Exception {
        String memberId = "user01";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/{memberId}/interest-books", memberId)
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.interestBookList").isArray())
                .andExpect(jsonPath("$.data.interestBookList[0].bookName").value("지구에서 한아뿐"))
                .andExpect(jsonPath("$.data.pagination.currentPage").value(1))
                .andExpect(jsonPath("$.data.pagination.totalPage").value(1))
                .andExpect(jsonPath("$.data.pagination.totalItems").value(1))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

    @DisplayName("관심 도서 등록하기")
    @Test
    void createInterestBookTest() throws Exception {
        InterestBookRequest request = new InterestBookRequest(5L);

        mockMvc.perform(post("/api/v1/interest-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.bookName").value("노르웨이의 숲"))
                .andReturn();
    }
}