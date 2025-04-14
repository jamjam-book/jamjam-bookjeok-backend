package com.jamjam.bookjeok.domains.payment.dto.response;

import com.jamjam.bookjeok.domains.order.dto.orderdetail.OrderDetailDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record PaymentConfirmResponse(
        List<OrderDetailDTO> orderDetails
) {
}