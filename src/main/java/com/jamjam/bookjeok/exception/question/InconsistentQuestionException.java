package com.jamjam.bookjeok.exception.question;

import lombok.Getter;

@Getter
public class InconsistentQuestionException extends RuntimeException {

    private final QuestionErrorCode questionErrorCode;

    public InconsistentQuestionException(QuestionErrorCode questionErrorCode) {
        super(questionErrorCode.getMessage());
        this.questionErrorCode = questionErrorCode;
    }
}
