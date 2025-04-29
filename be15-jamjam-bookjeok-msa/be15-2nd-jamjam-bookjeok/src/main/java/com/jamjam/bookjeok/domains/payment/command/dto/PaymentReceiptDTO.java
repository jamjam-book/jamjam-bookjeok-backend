package com.jamjam.bookjeok.domains.payment.command.dto;

import lombok.Builder;

@Builder
public record PaymentReceiptDTO(
        String url
) {
}