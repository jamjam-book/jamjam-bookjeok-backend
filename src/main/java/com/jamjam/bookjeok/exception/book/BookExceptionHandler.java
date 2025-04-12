package com.jamjam.bookjeok.exception.book;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.exception.book.category.BookCategoryNotFoundException;
import com.jamjam.bookjeok.exception.book.category.RegistPreexistingCategoryException;
import com.jamjam.bookjeok.exception.book.review.InconsistentReviewException;
import com.jamjam.bookjeok.exception.book.review.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(RegistPreexistingBookException.class)
    public ResponseEntity<ApiResponse<Void>> registPreexistingBookExceptionHandler(RegistPreexistingBookException e) {

        BookErrorCode errorCode = e.getBookErrorCode();
        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());

    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> bookNotFoundExceptionHandler(BookNotFoundException e) {

        BookErrorCode errorCode = e.getBookErrorCode();
        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());

    }

    @ExceptionHandler(BookCategoryNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> bookCategoryNotFoundExceptionHandler(BookCategoryNotFoundException e) {

        BookErrorCode errorCode = e.getBookErrorCode();
        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());

    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiResponse<Void>> fileStorageExceptionHandler(FileStorageException e) {
        String errorCode = HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
        String errorMessage = e.getMessage();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(errorCode, errorMessage));
    }

    @ExceptionHandler(RegistPreexistingCategoryException.class)
    public ResponseEntity<ApiResponse<Void>> registPreexistingCategoryHandler(RegistPreexistingCategoryException e){

        BookErrorCode errorCode = e.getBookErrorCode();
        ApiResponse<Void> response
                = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response,errorCode.getHttpStatus());

    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> reviewNotFoundExceptionHandler(ReviewNotFoundException e) {

        BookErrorCode errorCode = e.getBookErrorCode();
        ApiResponse<Void> response = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response, errorCode.getHttpStatus());

    }

    @ExceptionHandler(InconsistentReviewException.class)
    public ResponseEntity<ApiResponse<Void>> inconsistentReviewExceptionHandler(InconsistentReviewException e) {

        BookErrorCode errorCode = e.getBookErrorCode();
        ApiResponse<Void> response = ApiResponse.failure(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(response, errorCode.getHttpStatus());

    }

}
