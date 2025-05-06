package com.jamjam.bookjeok.domains.member.command.dto.response;

import com.jamjam.bookjeok.common.dto.Pagination;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InterestAuthorResponse {

    private String authorName;
    private final Pagination pagination;


}
