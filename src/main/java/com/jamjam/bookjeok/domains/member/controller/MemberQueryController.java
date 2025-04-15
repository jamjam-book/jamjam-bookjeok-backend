package com.jamjam.bookjeok.domains.member.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.dto.response.MemberListResponse;
import com.jamjam.bookjeok.domains.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryService memberQueryService;

    @GetMapping("/members/me")
    public ResponseEntity<ApiResponse<MemberDetailResponse>> getMemberDetail(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        MemberDetailResponse response = memberQueryService.getMemberDetail(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 간단한 URL 패턴보다 복잡한 비즈니스 로직이나 메서드 단위의 세밀한 보안 제어가 필요한 경우
    // @PreAuthorize : 메소드 호출 전에 조건 평가
    // @PostAuthorize : 반환 결과에 기반한 후 처리 보안
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/members")
    public ResponseEntity<ApiResponse<MemberListResponse>> getMembers() {
        MemberListResponse response = memberQueryService.getAllMembers();
        return ResponseEntity.ok(ApiResponse.success(response));
    }





}