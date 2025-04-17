package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;

public interface MemberQueryService {
    MemberDetailResponse getMemberDetail(String username);
}
