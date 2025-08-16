package com.jamjam.bookjeok.domains.payment.command.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PaymentConfirmRequest(
        @NotBlank(message = "orderId가 없으면 결제를 완료할 수 없습니다.") String orderId,
        @Min(value = 1, message = "올바르지 않은 결제 금액입니다.") int amount
) {
}