package com.jamjam.bookjeok.domains.member.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordModifyRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PasswordResetLinkRequest;
import com.jamjam.bookjeok.domains.member.command.service.PasswordRestCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PasswordResetCommandController {

    private final PasswordRestCommandService passwordRestCommandService;

    // 비밀번호 재설정 링크 요청
    @PostMapping("/password/reset-link")
    public ResponseEntity<ApiResponse<String>> requestReset(
            @RequestBody PasswordResetLinkRequest passwordResetLinkRequest
    ) {
        String token = passwordRestCommandService.requestPasswordReset(passwordResetLinkRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(token));
    }

    // 비밀번호 재설정 하기
    @PostMapping("/password/reset")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody PasswordModifyRequest passwordModifyRequest) {
        passwordRestCommandService.resetPassword(passwordModifyRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
