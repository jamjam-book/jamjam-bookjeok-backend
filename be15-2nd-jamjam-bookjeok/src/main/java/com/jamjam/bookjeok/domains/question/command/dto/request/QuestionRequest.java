package com.jamjam.bookjeok.domains.question.command.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record QuestionRequest(
        @NotNull(message = "작성자는 반드시 입력해야 합니다.") Long writerUid,
        @NotNull(message = "문의 항목은 반드시 선택해야 합니다.") Long questionCategoryId,
        @NotBlank(message = "제목은 반드시 입력해야 합니다.") String title,
        @NotBlank(message = "문의 내용은 반드시 입력해야 합니다.") String contents) {
}
