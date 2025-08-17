package com.jamjam.bookjeok.domains.pendingorder.command.dto.response;

import lombok.Builder;

@Builder
public record PendingOrderResponse(
        String orderId, int totalAmount
) {
}