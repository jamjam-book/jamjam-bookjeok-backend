package com.jamjam.bookjeok.exception.book;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {

    private final BookErrorCode bookErrorCode;

    public BookNotFoundException(BookErrorCode bookErrorCode) {
        super(bookErrorCode.getMessage());
        this.bookErrorCode = bookErrorCode;
    }

}
