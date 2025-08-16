package com.jamjam.bookjeok.auth.service;

import com.jamjam.bookjeok.auth.dto.LoginRequest;
import com.jamjam.bookjeok.auth.dto.TokenResponse;
import com.jamjam.bookjeok.auth.entity.RefreshToken;
import com.jamjam.bookjeok.auth.repository.RefreshTokenRepository;
import com.jamjam.bookjeok.domains.member.command.repository.MemberRepository;
import com.jamjam.bookjeok.domains.member.command.entity.Member;

import com.jamjam.bookjeok.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse login(LoginRequest request) {
        Member member = memberRepository.findByMemberId(request.getMemberId())
                .orElseThrow(() -> new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호"));

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("올바르지 않은 아이디 혹은 비밀번호");
        }

        // 로그인 성공 시 token 발급
        String accessToken = jwtTokenProvider.createToken(member.getMemberId(), member.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId(), member.getRole().name());

        RefreshToken tokenEntity = RefreshToken.builder()
                .memberId(member.getMemberId())
                .token(refreshToken)
                .expiryDate(
                        new Date(System.currentTimeMillis()
                                + jwtTokenProvider.getRefreshExpiration())
                )
                .build();

        refreshTokenRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponse refreshToken(String providedRefreshToken) {
        // 리프레시 토큰 유효성 검사
        jwtTokenProvider.validateToken(providedRefreshToken);
        String memberId = jwtTokenProvider.getMemberNameFromJWT(providedRefreshToken);

        // 저장 된 refresh token 조회
        RefreshToken storedToken = refreshTokenRepository.findRefreshTokenByMemberId(memberId)
                .orElseThrow(() -> new BadCredentialsException("해당 유저로 조회되는 리프레시 토큰 없음"));

        // 넘어온 리프레시 토큰 값과의 일치 확인
        if(!storedToken.getToken().equals(providedRefreshToken)) {
            throw new BadCredentialsException("리프레시 토큰 일치하지 않음");
        }

        // DB에 저장 된 만료일과 현재 시간 비교 (추가 검증)
        if(storedToken.getExpiryDate().before(new Date())) {
            throw new BadCredentialsException("리프레시 토큰 유효시간 만료");
        }

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BadCredentialsException("해당 리프레시 토큰을 위한 유저 없음"));

        // 새로운 토큰 재발급
        String accessToken = jwtTokenProvider.createToken(member.getMemberName(), member.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberName(), member.getRole().name());

        RefreshToken tokenEntity = RefreshToken.builder()
                .memberId(member.getMemberName())
                .token(refreshToken)
                .expiryDate(
                        new Date(System.currentTimeMillis()
                                + jwtTokenProvider.getRefreshExpiration())
                )
                .build();

        refreshTokenRepository.save(tokenEntity);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public void logout(String refreshToken) {
        // refresh token의 서명 및 만료 검증
        jwtTokenProvider.validateToken(refreshToken);
        String memberName = jwtTokenProvider.getMemberNameFromJWT(refreshToken);
        refreshTokenRepository.deleteById(memberName);    // DB에 저장 된 refresh token 삭제
    }
}