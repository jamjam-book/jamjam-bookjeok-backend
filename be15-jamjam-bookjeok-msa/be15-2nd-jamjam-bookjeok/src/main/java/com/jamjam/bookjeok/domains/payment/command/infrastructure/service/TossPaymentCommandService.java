package com.jamjam.bookjeok.domains.payment.command.infrastructure.service;

import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.TossPaymentApproveRequest;

public interface TossPaymentCommandService {

    PaymentDTO approvePayment(TossPaymentApproveRequest tossPaymentApproveRequest);

}