package com.jamjam.bookjeok.domains.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("멤버 전체 조회 테스트")
    @WithMockUser(authorities = "ADMIN")
    @Test
    void getAllMembersTest() throws Exception {
        mockMvc.perform(get("/api/v1/admin/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.memberList").isArray())
                .andExpect(jsonPath("$.timestamp").exists())
                .andDo(print());
    }

    @DisplayName("멤버의 아이디로 멤버를 검색하는 테스트")
    @WithMockUser(authorities = "ADMIN")
    @Test
    void getMemberByIdTest() throws Exception {
        String memberId = "user02";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/member")
                        .param("memberId", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data.member.memberId").value("user02"))
                .andExpect(jsonPath("$.data.member.memberName").value("유형진"))
                .andExpect(jsonPath("$.data.member.phoneNumber").value("01024001349"))
                .andExpect(jsonPath("$.data.member.email").value("test2@gmail.com"))
                .andExpect(jsonPath("$.data.member.nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data.member.birthDate").value("19970918"))
                .andExpect(jsonPath("$.data.member.marketingConsent").value(true))
                .andExpect(jsonPath("$.data.member.role").value("MEMBER"))
                .andExpect(jsonPath("$.data.member.createdAt").value("2025-02-06T14:13:32"))
                .andExpect(jsonPath("$.data.member.modifiedAt").isEmpty())
                .andExpect(jsonPath("$.data.member.activityStatus").value("ACTIVE"))
                .andDo(print());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DisplayName("멤버의 닉네임으로 멤버를 검색하는 테스트")
    void getMemberByNicknameTest() throws Exception {
        String nickname = "닉네임02";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/member")
                        .param("nickname", nickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data.member.memberId").value("user02"))
                .andExpect(jsonPath("$.data.member.memberName").value("유형진"))
                .andExpect(jsonPath("$.data.member.phoneNumber").value("01024001349"))
                .andExpect(jsonPath("$.data.member.email").value("test2@gmail.com"))
                .andExpect(jsonPath("$.data.member.nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data.member.birthDate").value("19970918"))
                .andExpect(jsonPath("$.data.member.marketingConsent").value(true))
                .andExpect(jsonPath("$.data.member.role").value("MEMBER"))
                .andExpect(jsonPath("$.data.member.createdAt").value("2025-02-06T14:13:32"))
                .andExpect(jsonPath("$.data.member.modifiedAt").isEmpty())
                .andExpect(jsonPath("$.data.member.activityStatus").value("ACTIVE"))
                .andDo(print());
    }
}

