package com.jamjam.bookjeok.domains.order.dto.cart.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartRequest(
        @NotNull(message = "memberUid는 비어있을 수 없습니다.") Long memberUid,
        @NotNull(message = "bookId는 비어있을 수 없습니다.") Long bookId,
        @NotBlank(message = "책 이름은 비어있을 수 없습니다.") String bookName,
        @Min(value = 1, message = "책의 가격은 0보다 작을 수 없습니다.") int price,
        @Min(value = 1, message = "책의 수량은 1개 보다 작을 수 없습니다.") int quantity
) {
}