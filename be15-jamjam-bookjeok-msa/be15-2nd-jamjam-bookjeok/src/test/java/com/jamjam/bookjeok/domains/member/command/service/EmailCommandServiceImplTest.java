package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class EmailCommandServiceImplTest {

    @Autowired
    private EmailCommandService emailCommandService;

    @DisplayName("비밀번호 재설정 이메일 보내기 테스트")
    @Test
    void sendPasswordResetEmailTest() throws Exception {
        Member member = Member.builder()
                .email("bookjeok@bookjeok.com")
                .memberId("jamjam123")
                .build();

        String token = "mock-token-123";

        emailCommandService.sendPasswordResetEmail(member, token);
    }
}