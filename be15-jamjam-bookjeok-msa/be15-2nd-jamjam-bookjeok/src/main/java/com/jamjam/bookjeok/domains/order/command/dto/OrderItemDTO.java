package com.jamjam.bookjeok.domains.order.command.dto;

import lombok.Builder;

@Builder
public record OrderItemDTO(
        Long bookId, String bookName, int quantity,
        int totalPrice, String imageUrl
) {
}