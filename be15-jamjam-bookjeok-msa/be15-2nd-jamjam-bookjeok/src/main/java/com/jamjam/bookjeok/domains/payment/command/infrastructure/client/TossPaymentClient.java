package com.jamjam.bookjeok.domains.payment.command.infrastructure.client;

import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.TossPaymentApproveRequest;

public interface TossPaymentClient {

    PaymentDTO requestPaymentApproval(TossPaymentApproveRequest request);

}