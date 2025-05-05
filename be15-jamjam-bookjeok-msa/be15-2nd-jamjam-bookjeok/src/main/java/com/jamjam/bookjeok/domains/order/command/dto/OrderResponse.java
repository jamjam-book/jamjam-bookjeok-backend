package com.jamjam.bookjeok.domains.order.command.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderResponse(
    Long orderUid, String orderId, String orderName, int totalAmount,
    String orderStatusName, LocalDateTime orderedAt, LocalDateTime canceledAt,
    LocalDateTime changedAt, LocalDateTime refundedAt, String imageUrl, int quantity
) {
}