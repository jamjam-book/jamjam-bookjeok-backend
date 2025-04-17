package com.jamjam.bookjeok.domains.pendingorder.command.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record PendingOrderRequest(
        @NotNull(message = "memberUid가 없으면 주문을 할 수 없습니다.") Long memberUid,
        List<@Valid PendingOrderBookItemsRequest> orderBookItems
) {
}