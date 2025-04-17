package com.jamjam.bookjeok.exception.book.review;

import com.jamjam.bookjeok.exception.book.BookErrorCode;
import lombok.Getter;

@Getter
public class InconsistentReviewException extends RuntimeException {

    private final BookErrorCode bookErrorCode;

    public InconsistentReviewException(BookErrorCode bookErrorCode) {
        super(bookErrorCode.getMessage());
        this.bookErrorCode = bookErrorCode;
    }
}
