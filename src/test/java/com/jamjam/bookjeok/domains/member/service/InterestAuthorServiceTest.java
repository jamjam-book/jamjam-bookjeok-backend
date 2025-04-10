package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.InterestAuthorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class InterestAuthorServiceTest {

    @Autowired
    private InterestAuthorService interestAuthorService;

    @DisplayName("회원의 책 즐겨찾기 목록 가져오기")
    @Test
    void findInterestAuthorByMemberIdTest(){
        String memberId = "user01";

        List<InterestAuthorDTO> interestAuthorList
                = interestAuthorService.getInterestAuthorList(memberId);

        assertNotNull(interestAuthorList);
    }
}
