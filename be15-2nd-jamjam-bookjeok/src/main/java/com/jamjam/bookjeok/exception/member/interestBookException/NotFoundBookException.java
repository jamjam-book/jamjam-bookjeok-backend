package com.jamjam.bookjeok.exception.member.interestBookException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class NotFoundBookException extends RuntimeException{

    private final MemberErrorCode memberErrorCode;

    public NotFoundBookException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}
