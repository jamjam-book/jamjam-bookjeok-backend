package com.jamjam.bookjeok.exception.cart;

public class CartItemLimitExceededException extends RuntimeException {

    public CartItemLimitExceededException(String message) {
        super(message);
    }

}