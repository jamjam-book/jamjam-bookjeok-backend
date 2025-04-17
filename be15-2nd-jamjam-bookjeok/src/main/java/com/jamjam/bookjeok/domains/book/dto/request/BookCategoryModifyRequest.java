package com.jamjam.bookjeok.domains.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BookCategoryModifyRequest(
        @NotBlank String categoryName,
        @NotBlank String newCategoryName
) {
}
