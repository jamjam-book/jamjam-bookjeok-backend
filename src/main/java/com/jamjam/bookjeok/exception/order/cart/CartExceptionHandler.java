package com.jamjam.bookjeok.exception.order.cart;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartExceptionHandler {

    @ExceptionHandler(CartItemLimitExceededException.class)
    public ResponseEntity<ApiResponse<Void>> cartItemLimitExceededExceptionHandler(CartItemLimitExceededException e) {
        String errorCode = HttpStatus.BAD_REQUEST.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String errorCode = HttpStatus.BAD_REQUEST.getReasonPhrase();
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

    @ExceptionHandler(CartBookNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> cartBookNotFoundExceptionHandler(CartBookNotFoundException e) {
        String errorCode = HttpStatus.NOT_FOUND.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

}