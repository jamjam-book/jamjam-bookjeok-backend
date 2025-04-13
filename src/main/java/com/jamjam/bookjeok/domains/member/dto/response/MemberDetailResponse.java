package com.jamjam.bookjeok.domains.member.dto.response;

import com.jamjam.bookjeok.domains.member.dto.MemberDTO;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class MemberDetailResponse {

    private MemberDTO member;

}