package com.jamjam.bookjeok.domains.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class FollowDTO {
    
    private String memberId;
    private String nickname;

}
