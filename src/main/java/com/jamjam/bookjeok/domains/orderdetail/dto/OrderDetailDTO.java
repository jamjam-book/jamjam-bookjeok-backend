package com.jamjam.bookjeok.domains.orderdetail.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderDetailDTO(
        String orderId, String bookName, String isbn,
        int totalPrice, int quantity, String imageUrl, LocalDateTime orderedAt
) {
}