package com.jamjam.bookjeok.domains.payment.command.dto;

import lombok.Builder;

@Builder
public record PaymentDTO(
        String paymentKey, String type, String orderId,
        String orderName, String method, int totalAmount,
        String requestedAt, String approvedAt
) {
}