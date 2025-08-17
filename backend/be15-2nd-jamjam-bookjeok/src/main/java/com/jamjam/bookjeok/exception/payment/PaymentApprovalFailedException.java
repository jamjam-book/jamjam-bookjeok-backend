package com.jamjam.bookjeok.exception.payment;

public class PaymentApprovalFailedException extends RuntimeException {

    public PaymentApprovalFailedException(String message) {
        super(message);
    }

}