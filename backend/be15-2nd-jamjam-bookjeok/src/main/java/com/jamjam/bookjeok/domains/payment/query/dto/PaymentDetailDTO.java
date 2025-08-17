package com.jamjam.bookjeok.domains.payment.query.dto;

import lombok.Builder;

@Builder
public record PaymentDetailDTO(
        int totalAmount, String paymentMethod
) {
}