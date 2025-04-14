package com.jamjam.bookjeok.domains.payment.infrastructure.service;

import com.jamjam.bookjeok.domains.payment.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.dto.request.PaymentConfirmRequest;

public interface TossPaymentService {

    PaymentDTO approvePayment(PaymentConfirmRequest paymentConfirmRequest);

}