package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberListResponse;

public interface AdminQueryService {
    MemberListResponse getAllMembers(MemberSearchRequest memberSearchRequest);

    MemberDetailResponse getMemberByMemberId(String memberId);
}
