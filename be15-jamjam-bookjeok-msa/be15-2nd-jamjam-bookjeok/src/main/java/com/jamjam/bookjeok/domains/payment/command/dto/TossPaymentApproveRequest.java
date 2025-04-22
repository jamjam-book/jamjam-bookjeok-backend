package com.jamjam.bookjeok.domains.payment.command.dto;

import lombok.Builder;

@Builder
public record TossPaymentApproveRequest(
        String paymentKey, String orderId, int amount
) {
}