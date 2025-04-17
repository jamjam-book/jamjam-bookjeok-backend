package com.jamjam.bookjeok.exception.payment;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentExceptionHandler {

    @ExceptionHandler(PaymentOrderNotFountException.class)
    public ResponseEntity<ApiResponse<Void>> handlePaymentOrderNotFountException(PaymentOrderNotFountException e) {
        String errorCode = HttpStatus.BAD_REQUEST.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

    @ExceptionHandler(BookInfoNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleBookInfoNotFoundException(BookInfoNotFoundException e) {
        String errorCode = HttpStatus.NOT_FOUND.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

    @ExceptionHandler(InsufficientBookStockException.class)
    public ResponseEntity<ApiResponse<Void>> handleInsufficientBookStockException(InsufficientBookStockException e) {
        String errorCode = HttpStatus.CONFLICT.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

    @ExceptionHandler(ExternalPaymentException.class)
    public ResponseEntity<ApiResponse<Void>> handleExternalPaymentException(ExternalPaymentException e) {
        String errorCode = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

}