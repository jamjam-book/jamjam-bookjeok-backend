package com.jamjam.bookjeok.exception.member;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{
    private final MemberErrorCode memberErrorCode;

    public MemberException(MemberErrorCode memberErrorCode){
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}
