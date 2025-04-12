package com.jamjam.bookjeok.domains.order.dto.pendingorder.response;

import lombok.Builder;

@Builder
public record PendingOrderResponse(
        String orderId, int totalAmount
) {
}