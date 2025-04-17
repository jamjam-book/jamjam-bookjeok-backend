package com.jamjam.bookjeok.exception.question;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QuestionExceptionHandler {

    @ExceptionHandler(NotFoundQuestionException.class)
    public ResponseEntity<ApiResponse<Void>> notFoundQuestionExceptionHandler(NotFoundQuestionException e) {

        QuestionErrorCode errorCode = e.getQuestionErrorCode();
        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());

    }

    @ExceptionHandler(InconsistentQuestionException.class)
    public ResponseEntity<ApiResponse<Void>> inconsistentQuestionExceptionHandler(InconsistentQuestionException e) {

        QuestionErrorCode errorCode = e.getQuestionErrorCode();
        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());

    }

}
