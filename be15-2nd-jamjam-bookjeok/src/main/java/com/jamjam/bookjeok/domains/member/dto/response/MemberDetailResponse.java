package com.jamjam.bookjeok.domains.member.dto.response;

import com.jamjam.bookjeok.domains.member.dto.MemberDTO;
import lombok.*;

@Getter
@Builder
public class MemberDetailResponse {

    private MemberDTO member;

}