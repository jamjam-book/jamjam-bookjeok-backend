package com.jamjam.bookjeok.domains.payment.command.dto;

import lombok.Builder;

@Builder
public record OrderItemsDTO(
        Long bookId, String bookName, int stockQuantity, int totalPrice
) {
}