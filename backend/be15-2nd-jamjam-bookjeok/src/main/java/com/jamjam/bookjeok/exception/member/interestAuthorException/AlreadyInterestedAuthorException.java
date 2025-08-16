package com.jamjam.bookjeok.exception.member.interestAuthorException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class AlreadyInterestedAuthorException extends RuntimeException {

    private final MemberErrorCode memberErrorCode;

    public AlreadyInterestedAuthorException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getCode());
        this.memberErrorCode = memberErrorCode;
    }
}
