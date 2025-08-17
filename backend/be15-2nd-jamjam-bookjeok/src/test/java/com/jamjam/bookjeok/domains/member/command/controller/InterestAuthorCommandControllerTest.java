package com.jamjam.bookjeok.domains.member.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.member.command.dto.request.InterestAuthorRequest;
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
public class InterestAuthorCommandControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    String baseUrl = "/api/v1/members";

    @DisplayName("관심 작가 등록하기")
    @Test
    void createInterestAuthorTest() throws Exception {
        String memberId = "user02";
        InterestAuthorRequest request = new InterestAuthorRequest("공지영", memberId);
        
        mockMvc.perform(post("/api/v1/members/interest/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.authorName").value("공지영"))
                .andReturn();
    }

    @DisplayName("관심 작가 삭제하기")
    @Test
    void deleteInterestAuthorTest() throws Exception {
        String memberId = "user02";
        Long authorId = 3L;

        mockMvc.perform(delete(baseUrl+"/{memberId}/interest/authors/{authorId}", memberId, authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true));
    }

}
