package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.query.dto.InterestAuthorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        List<InterestAuthorDTO> interestAuthorList
                = interestAuthorQueryService.getInterestAuthorList(memberId);

        assertNotNull(interestAuthorList);
    }
}
