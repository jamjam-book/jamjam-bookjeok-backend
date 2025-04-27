package com.jamjam.bookjeok.exception.member.passwordResetException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class ExpiredTokenException extends RuntimeException {

    private final MemberErrorCode memberErrorCode;

    public ExpiredTokenException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}
