package com.jamjam.bookjeok.domains.order.dto.cart.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CartBookListResponse(List<CartBookResponse> bookList) {
}