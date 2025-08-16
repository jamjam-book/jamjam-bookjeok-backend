package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.InterestAuthorRequest;

public interface InterestAuthorCommandService {
    String createInterestAuthor(InterestAuthorRequest request);

    void deleteInterestAuthor(String memberId, Long authorId);
}
