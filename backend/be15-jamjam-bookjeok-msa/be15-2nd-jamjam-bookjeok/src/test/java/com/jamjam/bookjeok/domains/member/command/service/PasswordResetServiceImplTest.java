package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordModifyRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordResetLinkRequest;
import com.jamjam.bookjeok.domains.member.command.entity.Member;
import com.jamjam.bookjeok.domains.member.command.entity.PasswordResetToken;
import com.jamjam.bookjeok.domains.member.command.repository.MemberRepository;
import com.jamjam.bookjeok.domains.member.command.repository.PasswordResetTokenRepository;
import com.jamjam.bookjeok.exception.member.MemberException;
import com.jamjam.bookjeok.exception.member.passwordResetException.ExpiredTokenException;
import com.jamjam.bookjeok.exception.member.passwordResetException.NotExistTokenException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/* Mock 객체를 이용해서 서비스 단위 테스트 진행*/
@ExtendWith(MockitoExtension.class)
class PasswordResetServiceImplTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EmailCommandServiceImpl emailService;

    @InjectMocks
    private PasswordResetCommandServiceImpl passwordResetService;


    @DisplayName("비밀번호 변경 요청하기")
    @Test
    void requestPasswordResetTest () throws MessagingException {
        String memberId = "user01";
        PasswordResetLinkRequest request = new PasswordResetLinkRequest(memberId);

        Member member = Member.builder()
                .memberId(memberId)
                .email("book123@gmail.com")
                .build();

        when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(member));
        // 이메일 전송 부분은 진행하지 않고 넘어가기
        doNothing().when(emailService).sendPasswordResetEmail(any(), any());

        String result = passwordResetService.requestPasswordReset(request);

        // then
        assertNotNull(result);
        verify(memberRepository).findByMemberId(memberId);
    }

    @DisplayName("비밀번호 변경 요청 시 회원이 없는 경우 발생하는 예외")
    @Test
    void setPasswordResetServiceMemberExceptionTest () {
        String memberId = "user01";
        PasswordResetLinkRequest request = new PasswordResetLinkRequest(memberId);

        when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.empty());

        assertThrows(MemberException.class,
                () -> passwordResetService.requestPasswordReset(request));
    }

    @DisplayName("비밀번호 재설정을 링크를 통한 비밀번호 변경 테스트")
    @Test
    void resetPasswordTest() {
        String token = "abc123";
        String newPassword = "newPassword";
        String encodedPassword = "encodePassword";

        PasswordModifyRequest request = new PasswordModifyRequest(token, newPassword);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .memberId("user01")
                .resetToken(token)
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                .build();

        Member member = Member.builder()
                .memberId("user01")
                .password("old_pw")
                .build();

        when(passwordResetTokenRepository.findByResetToken(token)).thenReturn(Optional.of(resetToken));
        when(memberRepository.findByMemberId("user01")).thenReturn(Optional.of(member));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        passwordResetService.resetPassword(request);

        assertEquals(encodedPassword, member.getPassword());

        // 토큰을 삭제하는 동작이 발생했는지 확인
        verify(passwordResetTokenRepository).delete(resetToken);
    }

    // 없는 토큰인 경우
    @DisplayName("토큰이 없는 경우 발생하는 예외")
    @Test
    void notExistTokenExceptionTest() {
        String invalidToken = "invalidToken";
        PasswordModifyRequest request = new PasswordModifyRequest(invalidToken, "newPassword");

        when(passwordResetTokenRepository.findByResetToken(invalidToken))
                .thenReturn(Optional.empty());

        assertThrows(NotExistTokenException.class, () -> {
            passwordResetService.resetPassword(request);
        });
    }


    // 유효 시간이 지난 토큰인 경우
    @DisplayName("유효 시간이 지난 토큰인 경우 발생하는 예외")
    @Test
    void expiryTimeExceptionTest() {
        String expiredToken = "expiredToken";
        PasswordModifyRequest request = new PasswordModifyRequest(expiredToken, "newPassword");

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .memberId("user01")
                .resetToken(expiredToken)
                .expiryTime(LocalDateTime.now().minusMinutes(1))
                .build();

        when(passwordResetTokenRepository.findByResetToken(expiredToken))
                .thenReturn(Optional.of(resetToken));

        assertThrows(ExpiredTokenException.class, () -> {
            passwordResetService.resetPassword(request);
        });
    }
}
