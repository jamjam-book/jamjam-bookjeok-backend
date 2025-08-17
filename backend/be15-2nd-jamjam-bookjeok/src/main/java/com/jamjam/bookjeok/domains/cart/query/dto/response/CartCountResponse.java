package com.jamjam.bookjeok.domains.cart.query.dto.response;

import lombok.Builder;

@Builder
public record CartCountResponse(
        int cartCount
) {
}