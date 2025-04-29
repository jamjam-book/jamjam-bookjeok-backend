package com.jamjam.bookjeok.domains.payment.command.dto;

import lombok.Builder;

@Builder
public record PaymentEasyPayDTO(
        String provider, int amount, int discountAmount
) {
}