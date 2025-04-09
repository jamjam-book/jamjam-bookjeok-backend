package com.jamjam.bookjeok.exception.book;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(RegistPreexistingBookException.class)
    public ResponseEntity<ApiResponse<Void>> registPreexistingBookExceptionHandler(RegistPreexistingBookException e) {

        String errorCode = HttpStatus.BAD_REQUEST.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(errorCode, errorMessage));

    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> bookNotFoundExceptionHandler(BookNotFoundException e) {

        String errorCode = HttpStatus.NOT_FOUND.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(errorCode, errorMessage));

    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiResponse<Void>> fileStorageExceptionHandler(FileStorageException e) {
        String errorCode = HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

}
