package com.jamjam.bookjeok.domains.order.command.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDTO(
        String orderId, LocalDateTime orderedAt,
        String orderStatusName, List<OrderItemDTO> items
) {
}