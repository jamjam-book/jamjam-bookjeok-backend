package com.jamjam.bookjeok.domains.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSearchRequest {

    private String memberId;
    private String nickname;

}
