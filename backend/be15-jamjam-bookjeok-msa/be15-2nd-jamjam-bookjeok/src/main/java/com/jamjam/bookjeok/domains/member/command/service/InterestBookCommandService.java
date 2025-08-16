package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.InterestBookRequest;

public interface InterestBookCommandService {
    String createInterestBook(InterestBookRequest request);

    void deleteInterestBook(String memberId, Long bookId);
}
