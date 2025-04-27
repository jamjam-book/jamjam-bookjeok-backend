package com.jamjam.bookjeok.domains.member.command.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordModifyRequest {

    @NotNull(message = "토큰은 비어 있을 수 없습니다.")
    private final String passwordResetToken;

    @NotNull(message = "사용자의 비밀번호는 비어 있을 수 없습니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이어야 합니다.")
    private final String newPassword;

}