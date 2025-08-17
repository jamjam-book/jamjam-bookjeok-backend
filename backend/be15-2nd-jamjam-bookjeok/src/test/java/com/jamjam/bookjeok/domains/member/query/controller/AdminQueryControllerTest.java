package com.jamjam.bookjeok.domains.member.query.controller;

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
class AdminQueryControllerTest {

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

    @DisplayName("전체 멤버에서 멤버의 아이디로 멤버를 검색하는 테스트")
    @WithMockUser(authorities = "ADMIN")
    @Test
    void getMemberByIdTest() throws Exception {
        String memberId = "user02";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/members")
                        .param("memberId", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data.memberList[0].memberId").value("user02"))
                .andExpect(jsonPath("$.data.memberList[0].memberName").value("유형진"))
                .andExpect(jsonPath("$.data.memberList[0].phoneNumber").value("01024001349"))
                .andExpect(jsonPath("$.data.memberList[0].email").value("test2@gmail.com"))
                .andExpect(jsonPath("$.data.memberList[0].nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data.memberList[0].marketingConsent").value(true))
                .andExpect(jsonPath("$.data.memberList[0].createdAt").value("2025-02-06T14:13:32"))
                .andExpect(jsonPath("$.data.memberList[0].activityStatus").value("ACTIVE"))
                .andDo(print());
    }

    @DisplayName("전체 멤버에서 멤버의 닉네임으로 멤버를 검색하는 테스트")
    @WithMockUser(authorities = "ADMIN")
    @Test
    void getMemberByNicknameTest() throws Exception {
        String nickname = "닉네임02";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/members")
                        .param("nickname", nickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data.memberList[0].memberUid").value(2))
                .andExpect(jsonPath("$.data.memberList[0].memberId").value("user02"))
                .andExpect(jsonPath("$.data.memberList[0].memberName").value("유형진"))
                .andExpect(jsonPath("$.data.memberList[0].phoneNumber").value("01024001349"))
                .andExpect(jsonPath("$.data.memberList[0].email").value("test2@gmail.com"))
                .andExpect(jsonPath("$.data.memberList[0].nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data.memberList[0].marketingConsent").value(true))
                .andExpect(jsonPath("$.data.memberList[0].createdAt").value("2025-02-06T14:13:32"))
                .andExpect(jsonPath("$.data.memberList[0].activityStatus").value("ACTIVE"))
                .andDo(print());
    }


    @DisplayName("멤버의 아이디로 특정 회원을 조회하는 테스트")
    @WithMockUser(authorities = "ADMIN")
    @Test
    void getMemberByMemberId() throws Exception {
        String memberId = "user02";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/members/{memberId}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data.member.memberUid").value(2))
                .andExpect(jsonPath("$.data.member.memberId").value("user02"))
                .andExpect(jsonPath("$.data.member.memberName").value("유형진"))
                .andExpect(jsonPath("$.data.member.phoneNumber").value("01024001349"))
                .andExpect(jsonPath("$.data.member.email").value("test2@gmail.com"))
                .andExpect(jsonPath("$.data.member.nickname").value("닉네임02"))
                .andExpect(jsonPath("$.data.member.marketingConsent").value(true))
                .andExpect(jsonPath("$.data.member.createdAt").value("2025-02-06T14:13:32"))
                .andExpect(jsonPath("$.data.member.activityStatus").value("ACTIVE"))
                .andDo(print());
    }

}

