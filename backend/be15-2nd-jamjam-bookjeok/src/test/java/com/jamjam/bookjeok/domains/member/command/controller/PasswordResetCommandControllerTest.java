package com.jamjam.bookjeok.domains.member.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordModifyRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordResetLinkRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PasswordResetCommandControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @DisplayName("비밀번호 재설정 링크 요청 테스트")
    @Test
    void requestResetTest() throws Exception {
        String memberId = "user01";

        PasswordResetLinkRequest request = new PasswordResetLinkRequest(memberId);

        mockMvc.perform(post("/api/v1/password/reset-link")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();
    }

    @DisplayName("비밀번호 재설정 테스트")
    @Test
    void resetPasswordTest() throws Exception {
        String token = "testToken";
        String newPassword = "newPassword";

        PasswordModifyRequest passwordModifyRequest = new PasswordModifyRequest(token, newPassword);

        mockMvc.perform(post("/api/v1/password/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordModifyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();
    }

}