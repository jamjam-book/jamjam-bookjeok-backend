package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestAuthorListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class InterestAuthorQueryServiceImplTest {

    @Autowired
    private InterestAuthorQueryService interestAuthorQueryService;

    @DisplayName("회원의 책 즐겨찾기 목록 가져오기")
    @Test
    void findInterestAuthorByMemberIdTest(){
        String memberId = "user02";

        PageRequest pageRequest = new PageRequest(1,10);

        InterestAuthorListResponse interestAuthorResponse
                = interestAuthorQueryService.getInterestAuthorList(memberId, pageRequest);

        assertNotNull(interestAuthorResponse);
    }
}
