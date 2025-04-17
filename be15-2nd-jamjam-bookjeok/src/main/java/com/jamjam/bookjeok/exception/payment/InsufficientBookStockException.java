package com.jamjam.bookjeok.exception.payment;

public class InsufficientBookStockException extends RuntimeException {

    public InsufficientBookStockException(String message) {
        super(message);
    }

}