package com.jamjam.bookjeok.exception.order.orderdetail;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderDetailExceptionHandler {

    @ExceptionHandler(OrderDetailNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> orderDetailNotFoundException(OrderDetailNotFoundException e) {
        String errorCode = HttpStatus.BAD_REQUEST.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

}