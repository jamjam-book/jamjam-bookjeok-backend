package com.jamjam.bookjeok.exception.order.cart;

public class CartItemLimitExceededException extends RuntimeException {

    public CartItemLimitExceededException(String message) {
        super(message);
    }

}