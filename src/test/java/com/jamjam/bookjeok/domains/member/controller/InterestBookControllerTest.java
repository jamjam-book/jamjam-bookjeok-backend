package com.jamjam.bookjeok.domains.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
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

}