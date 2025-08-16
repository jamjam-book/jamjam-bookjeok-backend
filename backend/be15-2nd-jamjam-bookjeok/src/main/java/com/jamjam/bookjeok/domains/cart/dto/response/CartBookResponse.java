package com.jamjam.bookjeok.domains.cart.dto.response;

import lombok.Builder;

@Builder
public record CartBookResponse(
        Long memberUid, Long bookId, String bookName,
        int quantity, int totalPrice, String imageUrl
) {
}