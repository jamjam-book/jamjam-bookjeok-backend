package com.jamjam.bookjeok.domains.member.command.dto.response;

import com.jamjam.bookjeok.common.dto.Pagination;
import com.jamjam.bookjeok.domains.member.query.dto.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.query.dto.InterestBookDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InterestAuthorListResponse {

    private List<InterestAuthorDTO> interestAuthorList;
    private final Pagination pagination;

}
