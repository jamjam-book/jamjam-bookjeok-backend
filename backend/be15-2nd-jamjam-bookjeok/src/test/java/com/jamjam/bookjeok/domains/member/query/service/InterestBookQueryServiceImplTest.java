package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestBookListResponse;
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
class InterestBookQueryServiceImplTest {

    @Autowired
    private InterestBookQueryService interestBookQueryService;

    @DisplayName("멤버의 책 즐겨 찾기 목록 가져오기")
    @Test
    void getInterestBookListByMemberIdTest(){
        String memberId = "user01";

        PageRequest pageRequest = new PageRequest(1,10);

        InterestBookListResponse response =
                interestBookQueryService.getInterestBookListByMemberId(memberId,pageRequest);

        assertNotNull(response);
        assertEquals(1, response.getInterestBookList().size());
        assertEquals(1, response.getPagination().getCurrentPage());
    }
}
