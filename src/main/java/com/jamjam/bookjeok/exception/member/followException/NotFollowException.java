package com.jamjam.bookjeok.exception.member.followException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class NotFollowException extends RuntimeException{

    private final MemberErrorCode memberErrorCode;

    public NotFollowException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}
