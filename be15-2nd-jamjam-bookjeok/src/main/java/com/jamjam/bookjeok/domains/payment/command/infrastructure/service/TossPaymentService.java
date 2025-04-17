package com.jamjam.bookjeok.domains.payment.command.infrastructure.service;

import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;

public interface TossPaymentService {

    PaymentDTO approvePayment(PaymentConfirmRequest paymentConfirmRequest);

}