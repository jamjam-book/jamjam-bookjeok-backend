package com.jamjam.bookjeok.domains.payment.command.dto;

import lombok.Builder;

@Builder
public record PaymentCancelDTO(
        int cancelAmount, String cancelReason, int refundableAmount,
        int easyPayDiscountAmount, String canceledAt, String transactionKey,
        String receiptKey, String cancelStatus, String cancelRequestId
) {
}