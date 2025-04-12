package com.jamjam.bookjeok.exception.book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum BookErrorCode {
    PREEXISTING_BOOK("1000", "이미 등록된 도서입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_ISBN("1001", "존재하지 않는 ISBN 입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_BOOK("1002", "존재하지 않는 도서입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_CATEGORY("1003", "존재하지 않는 카테고리 입니다.", HttpStatus.BAD_REQUEST),
    PREEXISTING_CATEGORY("1004", "이미 존재하는 카테고리 입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_REVIEW("1005","존재하지 않는 리뷰 입니다.", HttpStatus.BAD_REQUEST),
    INCONSISTENT_USER("1006", "리뷰 작성자가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    INCONSISTENT_BOOK("1007", "리뷰 도서가 일치하지 않습니다.", HttpStatus.BAD_REQUEST );

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}