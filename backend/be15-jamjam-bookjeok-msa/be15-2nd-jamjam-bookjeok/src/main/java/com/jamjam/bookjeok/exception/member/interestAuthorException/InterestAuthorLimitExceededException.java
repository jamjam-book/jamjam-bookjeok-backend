package com.jamjam.bookjeok.exception.member.interestAuthorException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class InterestAuthorLimitExceededException extends RuntimeException{

    private final MemberErrorCode memberErrorCode;

    public InterestAuthorLimitExceededException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getCode());
        this.memberErrorCode = memberErrorCode;
    }
}
