package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.MemberCreateRequest;

public interface MemberCommandService {
    void registerMember(MemberCreateRequest request);
}
