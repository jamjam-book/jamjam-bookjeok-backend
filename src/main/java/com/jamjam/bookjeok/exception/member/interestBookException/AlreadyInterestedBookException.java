package com.jamjam.bookjeok.exception.member.interestBookException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class AlreadyInterestedBookException extends RuntimeException{

    private final MemberErrorCode memberErrorCode;

    public AlreadyInterestedBookException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}
