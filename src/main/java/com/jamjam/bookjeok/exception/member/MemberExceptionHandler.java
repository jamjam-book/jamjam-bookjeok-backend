package com.jamjam.bookjeok.exception.member;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClient;

@RestControllerAdvice
public class MemberExceptionHandler {

    private final RestClient.Builder builder;

    public MemberExceptionHandler(RestClient.Builder builder) {
        this.builder = builder;
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ApiResponse> handleBusinessException(MemberException e){
        MemberErrorCode errorCode = e.getMemberErrorCode();
        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());
    }


//    /* 입력 값 검증 */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e){
//        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
//        StringBuilder errorMessage = new StringBuilder(errorCode.getMessage());
//
//        /* 어떤 필드에서 어떤 오류가 나는지 */
//        for(FieldError error : e.getBindingResult().getFieldErrors()){
//            errorMessage.append(String.format("[%s : %s]", error.getField(), error.getDefaultMessage()));
//        }
//
//        ApiResponse<Void> response
//                = ApiResponse.failure(errorCode.getCode(), errorMessage.toString());
//
//        return new ResponseEntity<>(response,errorCode.getHttpStatus());
//    }

//    /* 상위 타입의 예외 하나 선언하기 : 위의 2가지 예외를 제외하고선 만들면 되는 것*/
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse> handleException(){
//        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
//
//        ApiResponse<Void> response
//                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());
//
//        return new ResponseEntity<>(response,errorCode.getHttpStatus());
//    }

}
