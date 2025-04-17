package com.jamjam.bookjeok.domains.payment.command.dto.response;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record PaymentConfirmResponse(
        List<OrderDetailDTO> orderDetails
) {
}