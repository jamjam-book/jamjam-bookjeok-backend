package com.jamjam.bookjeok.exception.book;

import lombok.Getter;

@Getter
public class RegistPreexistingCategoryException extends RuntimeException
{
    private final BookErrorCode bookErrorCode;

    public RegistPreexistingCategoryException(BookErrorCode bookErrorCode) {
        super(bookErrorCode.getMessage());
        this.bookErrorCode = bookErrorCode;
    }
}
