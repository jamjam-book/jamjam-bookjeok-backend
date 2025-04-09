package com.jamjam.bookjeok.exception.order.cart;

public class CartBookNotFoundException extends RuntimeException {

    public CartBookNotFoundException(String message) {
        super(message);
    }

}