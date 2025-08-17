package com.jamjam.bookjeok.domains.orderdetail.query.dto;

import lombok.Builder;

@Builder
public record OrderDetailBookDTO(
        Long bookId, String bookName, String isbn,
        int quantity, int totalPrice, String imageUrl
) {
}