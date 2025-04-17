package com.jamjam.bookjeok.exception.question;

import lombok.Getter;

@Getter
public class NotFoundQuestionException extends RuntimeException {

    private final QuestionErrorCode questionErrorCode;

    public NotFoundQuestionException(QuestionErrorCode questionErrorCode)
    {
        super(questionErrorCode.getMessage());
        this.questionErrorCode = questionErrorCode;
    }
}
