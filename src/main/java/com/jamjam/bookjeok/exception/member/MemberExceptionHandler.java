package com.jamjam.bookjeok.exception.member;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.exception.member.followException.AlreadyFollowException;
import com.jamjam.bookjeok.exception.member.followException.NotFollowException;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AlreadyInterestedAuthorException;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AuthorNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse> handleMemberException(MemberException e){
        MemberErrorCode errorCode = e.getMemberErrorCode();

        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ApiResponse> handleAuthorNotFoundException(AuthorNotFoundException e){
        MemberErrorCode errorCode = e.getMemberErrorCode();

        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());
    }

    @ExceptionHandler(AlreadyInterestedAuthorException.class)
    public ResponseEntity<ApiResponse> handleAlreadyInterestedAuthorException(AlreadyInterestedAuthorException e){
        MemberErrorCode errorCode = e.getMemberErrorCode();

        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());
    }

    @ExceptionHandler(AlreadyFollowException.class)
    public ResponseEntity<ApiResponse> handleAlreadyFollowException(AlreadyFollowException e){
        MemberErrorCode errorCode = e.getMemberErrorCode();

        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());
    }

    @ExceptionHandler(NotFollowException.class)
    public ResponseEntity<ApiResponse> handleNotFollowException(NotFollowException e){
        MemberErrorCode errorCode = e.getMemberErrorCode();

        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());
    }
}
