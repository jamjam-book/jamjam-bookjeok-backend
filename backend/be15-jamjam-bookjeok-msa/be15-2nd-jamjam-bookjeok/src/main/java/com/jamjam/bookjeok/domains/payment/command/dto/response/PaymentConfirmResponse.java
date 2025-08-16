package com.jamjam.bookjeok.domains.payment.command.dto.response;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.response.OrderDetailResponse;
import lombok.Builder;

@Builder
public record PaymentConfirmResponse(
        OrderDetailResponse orderDetails
) {
}