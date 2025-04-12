package com.jamjam.bookjeok.exception.book;

import lombok.Getter;

@Getter
public class BookCategoryNotFoundException extends RuntimeException {
    private final BookErrorCode bookErrorCode;

    public BookCategoryNotFoundException(BookErrorCode bookErrorCode) {
        super(bookErrorCode.getMessage());
        this.bookErrorCode = bookErrorCode;
    }
}
