package com.jamjam.bookjeok.domains.order.command.dto;

import lombok.Builder;

@Builder
public record OrderItemDTO(
        String bookName, int quantity,
        int totalPrice, String imageUrl
) {
}