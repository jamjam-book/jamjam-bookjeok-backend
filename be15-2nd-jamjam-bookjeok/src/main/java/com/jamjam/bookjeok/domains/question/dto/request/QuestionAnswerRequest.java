package com.jamjam.bookjeok.domains.question.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record QuestionAnswerRequest(
        @NotNull(message = "문의사항 ID 확인이 필요합니다.") Long questionId,
        @NotNull(message = "관리자 ID 확인이 필요합니다.") Long writerUid,
        @NotBlank(message = "답변이 입력되지 않았습니다.") String contents) {}
