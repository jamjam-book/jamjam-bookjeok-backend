package com.jamjam.bookjeok.domains.member.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.member.command.dto.request.FollowMemberRequest;
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
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FollowCommandControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("팔로우 하기 테스트")
    @Test
    void createFollowTest() throws Exception {
        String followerId = "user01";
        String followingId = "user04";

        FollowMemberRequest followMemberRequest = new FollowMemberRequest(followingId);

        mockMvc.perform(post("/api/v1/{followerId}/follow", followerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(followMemberRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nickname").value("닉네임04"))
                .andReturn();
    }

    @DisplayName("팔로우 취소하기 테스트")
    @Test
    void deleteFollowTest() throws Exception {
        String followerId = "user01";
        String followingId = "user02";

        FollowMemberRequest followMemberRequest = new FollowMemberRequest(followingId);

        mockMvc.perform(delete("/api/v1/{followerId}/follow", followerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(followMemberRequest)))
                .andExpect(jsonPath("$.success").value(true));
    }
}