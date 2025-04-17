package com.jamjam.bookjeok.domains.member.command.dto.response;

import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import lombok.*;

@Getter
@Builder
public class MemberDetailResponse {

    private MemberDTO member;

}