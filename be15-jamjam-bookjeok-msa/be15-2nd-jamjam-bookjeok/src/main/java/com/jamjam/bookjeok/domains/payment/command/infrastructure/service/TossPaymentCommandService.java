package com.jamjam.bookjeok.domains.payment.command.infrastructure.service;

import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;

public interface TossPaymentCommandService {

    PaymentDTO approvePayment(PaymentConfirmRequest paymentConfirmRequest);

}