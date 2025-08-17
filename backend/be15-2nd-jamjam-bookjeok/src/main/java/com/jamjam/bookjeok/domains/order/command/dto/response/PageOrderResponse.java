package com.jamjam.bookjeok.domains.order.command.dto.response;

import com.jamjam.bookjeok.domains.order.command.dto.OrderDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record PageOrderResponse(
        List<OrderDTO> orders, int pageNumber, int pageSize,
        long totalElements, int totalPages
) {
}