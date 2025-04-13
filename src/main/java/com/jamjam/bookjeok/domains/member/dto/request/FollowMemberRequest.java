package com.jamjam.bookjeok.domains.member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowMemberRequest {

    @NotNull(message = "팔로워의 아이디는 비어 있을 수 없습니다.")
    private final String followingId;

}
