package com.jamjam.bookjeok.domains.member.query.controller;

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
class FollowQueryControllerTest {

    @Autowired
    MockMvc mockMvc;

    String baseUrl = "/api/v1";

    @DisplayName("특정 멤버의 팔로우 목록 가져오기")
    @Test
    void getFollowListByMemberUidTest() throws Exception {
        String memberId = "user01";

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/members/{memberId}/followings", memberId))
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

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+ "/{writerId}/posts", writerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data[0].title").value("코딩 입문자"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());

    }

}