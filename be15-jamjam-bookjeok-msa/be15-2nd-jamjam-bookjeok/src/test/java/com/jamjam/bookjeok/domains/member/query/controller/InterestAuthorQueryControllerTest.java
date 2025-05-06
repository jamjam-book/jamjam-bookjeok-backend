package com.jamjam.bookjeok.domains.member.query.controller;

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
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class InterestAuthorQueryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("멤버의 아이디로 즐겨찾기 한 작가 목록 가져오기")
    @Test
    void getInterestAuthorByMemberIdTest() throws Exception {
        String memberId = "user02";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members/{memberId}/interest/authors", memberId)
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].authorName").value("정세랑"))
                .andExpect(jsonPath("$.data[0].bookList[0].bookName").value("지구에서 한아뿐"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

}
