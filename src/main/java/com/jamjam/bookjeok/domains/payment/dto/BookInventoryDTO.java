package com.jamjam.bookjeok.domains.payment.dto;

import lombok.Builder;

@Builder
public record BookInventoryDTO(
    Long bookId, String bookName, int stockQuantity
) {
}