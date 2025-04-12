package com.jamjam.bookjeok.domains.member.dto.response;

import com.jamjam.bookjeok.common.dto.Pagination;
import com.jamjam.bookjeok.domains.member.dto.InterestBookDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InterestBookListResponse {

    private List<InterestBookDTO> interestBookList;
    private final Pagination pagination;

}
