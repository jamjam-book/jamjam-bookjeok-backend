package com.jamjam.bookjeok.domains.payment.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PendingOrderDTO(
        Long memberUid, String orderId, int totalAmount,
        List<OrderItemsDTO> orderData
) {
}