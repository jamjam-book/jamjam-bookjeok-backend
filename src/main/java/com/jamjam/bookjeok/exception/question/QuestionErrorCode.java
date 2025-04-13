package com.jamjam.bookjeok.exception.question;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum QuestionErrorCode {

    INCONSISTENT_ANSWER("1000", "답변이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOTFOUND_ANSWER("1001", "존재하지 않는 답변입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}
