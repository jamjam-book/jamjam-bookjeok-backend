package com.jamjam.bookjeok.domains.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.member.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.PostSummaryDTO;
import com.jamjam.bookjeok.domains.member.service.FollowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.Mockito.when;
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

    @MockitoBean
    private FollowService followService;

    @DisplayName("특정 멤버의 팔로우 목록 가져오기")
    @Test
    void getFollowListByMemberUidTest() throws Exception {
        String memberId = "user01";

        List<FollowDTO> mockFollowList = List.of(
                new FollowDTO("user02", "닉네임02"),
                new FollowDTO("user03", "닉네임03")
        );

        when(followService.getFollowingListByMemberId(memberId)).thenReturn(mockFollowList);

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

        List<PostSummaryDTO> mockPostList = List.of(
                new PostSummaryDTO("닉네임02", "제목01"),
                new PostSummaryDTO("닉네임02", "제목02")
        );


        when(followService.getPostListByWriterId(writerId)).thenReturn(mockPostList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/{writerId}/postList", writerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data[0].title").value("제목01"))
                .andExpect(jsonPath("$.data[1].nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data[1].title").value("제목02"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());

    }


}