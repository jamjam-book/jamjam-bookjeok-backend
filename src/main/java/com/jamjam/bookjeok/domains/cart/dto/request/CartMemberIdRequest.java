package com.jamjam.bookjeok.domains.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartMemberIdRequest(
        @NotNull(message = "memberUid는 필수입니다.") Long memberUid) {
}