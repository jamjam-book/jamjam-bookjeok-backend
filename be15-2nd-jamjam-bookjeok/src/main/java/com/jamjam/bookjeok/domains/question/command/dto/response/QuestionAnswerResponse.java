package com.jamjam.bookjeok.domains.question.command.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QuestionAnswerResponse(
        Long answerId, Long questionId, Long writerUid,
        String contents, LocalDateTime modifiedAt, boolean isDeleted) {}
