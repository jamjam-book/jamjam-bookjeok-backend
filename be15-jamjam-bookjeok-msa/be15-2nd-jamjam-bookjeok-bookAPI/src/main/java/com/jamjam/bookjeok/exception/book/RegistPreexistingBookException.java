package com.jamjam.bookjeok.exception.book;

import lombok.Getter;

@Getter
public class RegistPreexistingBookException extends RuntimeException {

    private final BookErrorCode bookErrorCode;

    public RegistPreexistingBookException(BookErrorCode bookErrorCode) {
        super(bookErrorCode.getMessage());
        this.bookErrorCode = bookErrorCode;
    }
}
