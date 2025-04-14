package com.jamjam.bookjeok.domains.payment.dto;

import lombok.Builder;

@Builder
public record OrderItemsDTO(
        Long bookId, String bookName, int stockQuantity, int totalPrice
) {
}