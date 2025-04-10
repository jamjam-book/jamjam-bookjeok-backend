package com.jamjam.bookjeok.exception.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum MemberErrorCode {
    NOT_FOLLOW("1000", "팔로우 하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_MEMBER("1001", "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}