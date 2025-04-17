package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.InterestBookRequest;

public interface InterestBookCommandService {
    String createInterestBook(Long memberUid, InterestBookRequest request);

    void deleteInterestBook(Long memberUid, InterestBookRequest request);
}
