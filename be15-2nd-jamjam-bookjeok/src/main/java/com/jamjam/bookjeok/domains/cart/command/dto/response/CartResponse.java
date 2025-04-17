package com.jamjam.bookjeok.domains.cart.command.dto.response;

import lombok.Builder;

@Builder
public record CartResponse(
        Long memberUid, Long bookId, String bookName, int totalPrice, int quantity
) {
}