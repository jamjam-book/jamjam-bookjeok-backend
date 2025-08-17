package com.jamjam.bookjeok.exception.member.passwordResetException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class NotExistTokenException extends RuntimeException {

    private final MemberErrorCode memberErrorCode;

    public NotExistTokenException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}