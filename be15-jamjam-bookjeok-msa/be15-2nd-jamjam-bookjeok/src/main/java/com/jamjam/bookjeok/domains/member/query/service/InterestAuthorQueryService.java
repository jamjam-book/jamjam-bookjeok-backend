package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.query.dto.InterestAuthorDTO;

import java.util.List;

public interface InterestAuthorQueryService {
    List<InterestAuthorDTO> getInterestAuthorList(String memberId);
}
