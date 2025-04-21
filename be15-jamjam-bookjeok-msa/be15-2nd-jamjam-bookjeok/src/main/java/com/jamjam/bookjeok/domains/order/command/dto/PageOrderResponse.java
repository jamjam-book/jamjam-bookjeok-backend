package com.jamjam.bookjeok.domains.order.command.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PageOrderResponse(
        List<OrderResponse> orders, int pageNumber, int pageSize,
        long totalElements, int totalPages
) {
}