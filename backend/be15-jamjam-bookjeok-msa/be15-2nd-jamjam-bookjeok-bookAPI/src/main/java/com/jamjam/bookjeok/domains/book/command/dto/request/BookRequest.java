package com.jamjam.bookjeok.domains.book.command.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookRequest (
        @NotNull(message = "출판사는 비어있을 수 없습니다.") Long publisherId,
        @NotNull(message = "카테고리는 비어있을 수 없습니다.") Long categoryId,
        @NotBlank(message = "책 이름은 비어있을 수 없습니다.") String bookName,
        @NotBlank(message = "isbn 번호는 비어있을 수 없습니다.") String isbn,
        @NotNull(message = "출판일은 비어있을 수 없습니다.")LocalDate publishedAt,
        @Min(value = 0, message = "책의 가격은 0보다 작을 수 없습니다.") int price,
        @Min(value = 1, message = "책의 수량은 1개 보다 작을 수 없습니다.") int stockQuantity,
        String bookInfo) {

}
