package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestAuthorListResponse;

public interface InterestAuthorQueryService {
    InterestAuthorListResponse getInterestAuthorList(String memberId, PageRequest pageRequest);
}
