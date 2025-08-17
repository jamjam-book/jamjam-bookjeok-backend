package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailCommandServiceImplTest {

    @Mock
    private EmailCommandService emailCommandService;

    @DisplayName("비밀번호 재설정 이메일 보내기 테스트")
    @Test
    void sendPasswordResetEmailTest() throws Exception {
        Member member = Member.builder()
                .email("jamjam123@bookbook.com")
                .memberId("jamjam123")
                .build();
        String token = "mock-token-123";

        emailCommandService.sendPasswordResetEmail(member, token);

        verify(emailCommandService).sendPasswordResetEmail(member, token);
    }
}