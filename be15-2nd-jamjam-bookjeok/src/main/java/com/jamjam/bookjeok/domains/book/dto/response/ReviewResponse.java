package com.jamjam.bookjeok.domains.book.dto.response;

import lombok.Builder;

@Builder
public record ReviewResponse(Long memberUid, Long bookId, String content, int rating) {}
