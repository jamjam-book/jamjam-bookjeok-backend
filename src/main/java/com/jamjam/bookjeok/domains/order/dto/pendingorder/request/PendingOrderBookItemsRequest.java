package com.jamjam.bookjeok.domains.order.dto.pendingorder.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PendingOrderBookItemsRequest(
        @NotNull(message = "bookId는 필수 입니다.") Long bookId,
        @NotBlank(message = "도서명은 필수 입니다.") String bookName,
        @Min(value = 1, message = "수량은 1개 이상이어야 합니다.") int quantity,
        @Min(value = 1, message = "가격은 0 이하일 수 없습니다.") int totalPrice
) {
}