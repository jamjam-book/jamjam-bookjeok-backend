package com.jamjam.bookjeok.domains.order.command.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderResponse(
    Long orderUid, Long memberUid, String orderId, String bookName, int totalPrice,
    String orderStatusName, LocalDateTime orderedAt, LocalDateTime canceledAt,
    LocalDateTime changedAt, LocalDateTime refundedAt, String imageUrl, int quantity
) {
}