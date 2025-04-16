package com.jamjam.bookjeok.domains.cart.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CartBookListResponse(List<CartBookResponse> bookList) {
}