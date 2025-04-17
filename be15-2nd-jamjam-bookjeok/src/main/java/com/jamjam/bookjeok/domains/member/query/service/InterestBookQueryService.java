package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestBookListResponse;

public interface InterestBookQueryService {
    InterestBookListResponse getInterestBookListByMemberId(String memberId, PageRequest pageRequest);
}
