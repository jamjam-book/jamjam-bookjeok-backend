package com.jamjam.bookjeok.exception.member.followException;

import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import lombok.Getter;

@Getter
public class AlreadyFollowException extends RuntimeException{
    private final MemberErrorCode memberErrorCode;

    public AlreadyFollowException(MemberErrorCode memberErrorCode){
        super(memberErrorCode.getMessage());
        this.memberErrorCode = memberErrorCode;
    }
}
