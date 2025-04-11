package com.jamjam.bookjeok.exception.member.interestAuthorException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class AuthorNotFoundException extends RuntimeException{
    private final MemberErrorCode memberErrorCode;

    public AuthorNotFoundException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getCode());
        this.memberErrorCode = memberErrorCode;
    }
}
