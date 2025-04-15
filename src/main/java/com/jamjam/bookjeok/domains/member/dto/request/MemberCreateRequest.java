package com.jamjam.bookjeok.domains.member.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreateRequest {
    private final String memberId;
    private final String password;
    private final String memberName;
    private final String phoneNumber;
    private final String email;
    private final String nickname;
    private final String birthDate;
    private final String activityStatus = "ACTIVE";

}