package com.jamjam.bookjeok.domains.payment.service;

import com.jamjam.bookjeok.domains.payment.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.domains.payment.dto.response.PaymentConfirmResponse;

public interface PaymentService {

    PaymentConfirmResponse confirmPayment(PaymentConfirmRequest paymentConfirmRequest);

}