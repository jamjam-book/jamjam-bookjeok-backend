package com.jamjam.bookjeok.domains.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.member.dto.request.FollowMemberRequest;
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
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class FollowControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("특정 멤버의 팔로우 목록 가져오기")
    @Test
    void getFollowListByMemberUidTest() throws Exception {
        String memberId = "user01";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{memberId}/followings", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].memberId").value("user02"))
                .andExpect(jsonPath("$.data[0].nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data[1].memberId").value("user03"))
                .andExpect(jsonPath("$.data[1].nickname").value("닉네임03"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

    @DisplayName("팔로우하는 멤버의 게시글 목록 가져오기")
    @Test
    void getPostListByWriterIdTest() throws Exception {
        String writerId = "user02";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{writerId}/postList", writerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data[0].title").value("코딩 입문자"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());

    }

    @DisplayName("팔로우 하기")
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

}