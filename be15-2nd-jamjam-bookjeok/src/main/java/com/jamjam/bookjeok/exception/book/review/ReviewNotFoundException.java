package com.jamjam.bookjeok.exception.book.review;

import com.jamjam.bookjeok.exception.book.BookErrorCode;
import lombok.Getter;

@Getter
public class ReviewNotFoundException extends RuntimeException {

    private final BookErrorCode bookErrorCode;

    public ReviewNotFoundException(BookErrorCode bookErrorCode) {
        super(bookErrorCode.getMessage());
        this.bookErrorCode = bookErrorCode;
    }

}
