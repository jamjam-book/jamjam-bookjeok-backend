package com.jamjam.bookjeok.domains.member.command.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class MemberSearchRequest {
    @Min(1)
    private Integer page;

    @Min(1)
    private Integer size;

    private String memberId;

    private String nickname;

    public MemberSearchRequest(Integer page, Integer size, String memberId, String nickname) {
        this.page = (page != null) ? page : 1;
        this.size = (size != null) ? size : 10;
        this.memberId = memberId;
        this.nickname = nickname;
    }

    public int getOffset() {
        return (page - 1) * size;
    }

    public int getLimit() {
        return size;
    }
}
