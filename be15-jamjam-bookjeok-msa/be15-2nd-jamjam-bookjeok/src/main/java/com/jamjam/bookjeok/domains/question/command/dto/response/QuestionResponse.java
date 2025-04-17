package com.jamjam.bookjeok.domains.question.command.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QuestionResponse(
        Long questionId, Long writerUid, Long questionCategoryId,
        String title, String contents, LocalDateTime createdAt,
        LocalDateTime modifiedAt, boolean isDeleted) {}
