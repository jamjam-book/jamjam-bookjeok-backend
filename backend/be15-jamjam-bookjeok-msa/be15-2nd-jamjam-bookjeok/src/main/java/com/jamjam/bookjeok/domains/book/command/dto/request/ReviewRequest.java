package com.jamjam.bookjeok.domains.book.command.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReviewRequest(
        @NotNull(message = "도서 정보는 비어있을 수 없습니다.") Long bookId,
        @NotNull(message = "작성자 정보는 비어있을 수 없습니다.") Long memberUid,
        @NotBlank(message = "리뷰 내용은 비어있을 수 없습니다.") String content,
        @Max(value = 5, message = "평점은 5점을 초과할 수 없습니다.")
        @Min(value = 1, message = "평점은 1점 미만일 수 없습니다.") int rating) {}
