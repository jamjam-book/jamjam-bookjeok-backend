package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordModifyRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordResetLinkRequest;
import com.jamjam.bookjeok.domains.member.command.entity.PasswordResetToken;
import com.jamjam.bookjeok.domains.member.command.repository.MemberRepository;
import com.jamjam.bookjeok.domains.member.command.repository.PasswordResetTokenRepository;
import com.jamjam.bookjeok.domains.member.command.entity.Member;
import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import com.jamjam.bookjeok.exception.member.MemberException;
import com.jamjam.bookjeok.exception.member.passwordResetException.ExpiredTokenException;
import com.jamjam.bookjeok.exception.member.passwordResetException.NotExistTokenException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
/* 멤버가 모두 완성되면 멤버 서비스 쪽에 같이 합칠 예정 -> 비밀번호 재설정 서비스를 따로 분리할 필요가 없음*/
public class PasswordResetCommandServiceImpl implements PasswordRestCommandService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final EmailCommandServiceImpl emailCommandService;

    @Override
    @Transactional
    public String requestPasswordReset(PasswordResetLinkRequest passwordResetLinkRequest) {
        // 멤버의 아이디로 멤버가 존재하는지 확인하기
        String memberId = passwordResetLinkRequest.getMemberId();
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_EXIST_MEMBER));

        // 토큰 생성하기 (토큰 생성은 나중에 따로 클래스를 분리)
        String token = UUID.randomUUID().toString();
        // 토큰 만료 시간은 30분으로 설정
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(30);
        // 토큰 생성하기
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .memberId(memberId)
                .resetToken(token)
                .expiryTime(expirationTime)
                .build();
        // 토큰 저장하기
        passwordResetTokenRepository.save(passwordResetToken);

        try {
            // 비밀번호 재설정 이메일 보내기 (토큰이 포함되어 있는 이메일이 발송 됨)
            emailCommandService.sendPasswordResetEmail(member, token);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }

        return token;
    }

    @Override
    @Transactional
    public void resetPassword(PasswordModifyRequest passwordModifyRequest) {
        String token = passwordModifyRequest.getPasswordResetToken();
        String newPassword = passwordModifyRequest.getNewPassword();

        // 만약 토큰이 없다면
        PasswordResetToken resetToken = passwordResetTokenRepository.findByResetToken(token)
                .orElseThrow(() -> new NotExistTokenException(MemberErrorCode.NOT_EXIST_PASSWORD_RESET_TOKEN));

        // 만약 토큰의 시간이 만료 됐다면
        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException(MemberErrorCode.EXPIRED_PASSWORD_RESET_TOKEN);
        }

        // 멤버의 아이디로 멤버를 가져오기
        Member member = memberRepository.findByMemberId(resetToken.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_EXIST_MEMBER));

        // 비밀번호 변경하기
        member.setEncodedPassword(passwordEncoder.encode(newPassword));

        // 비밀번호 변경 직후 토큰 삭제하기
        passwordResetTokenRepository.delete(resetToken);
    }
}
