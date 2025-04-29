package com.jamjam.bookjeok.domains.payment.command.dto;

import lombok.Builder;

@Builder
public record PaymentCardDTO(
        int amount, String issuerCode, String acquirerCode, String number,
        int installmentPlanMonths, String approveNo, String cardType,
        String ownerType, String acquireStatus,  boolean isInterestFree
) {
}