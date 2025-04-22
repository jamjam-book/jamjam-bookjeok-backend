package com.jamjam.bookjeok.domains.payment.command.service;

import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.domains.payment.command.dto.response.PaymentConfirmResponse;

public interface PaymentCommandService {

    PaymentConfirmResponse confirmPayment(String paymentKey, PaymentConfirmRequest paymentConfirmRequest);

}