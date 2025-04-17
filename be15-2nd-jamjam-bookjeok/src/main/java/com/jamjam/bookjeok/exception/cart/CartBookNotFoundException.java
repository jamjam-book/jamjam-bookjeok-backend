package com.jamjam.bookjeok.exception.cart;

public class CartBookNotFoundException extends RuntimeException {

    public CartBookNotFoundException(String message) {
        super(message);
    }

}