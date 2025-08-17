package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.query.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.query.dto.PostSummaryDTO;
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
class FollowQueryServiceImplTest {

    @Autowired
    private FollowQueryService followQueryService;

    @DisplayName("멤버의 id 통해 팔로우 목록 가져오기")
    @Test
    void findFollowingListByMemberId() {
        String memberId = "user01";

        List<FollowDTO> followList = followQueryService.getFollowingListByMemberId(memberId);

        assertNotNull(followList);
        assertEquals(2, followList.size());
        assertEquals("user02", followList.get(0).getMemberId());
        assertEquals("닉네임02", followList.get(0).getNickname());
        assertEquals("user03", followList.get(1).getMemberId());
        assertEquals("닉네임03", followList.get(1).getNickname());
    }


    @DisplayName("팔로우한 사용자의 게시글 목록 조회")
    @Test
    void getPostsByWriterIdTest() {
        String writerId = "user02";

        List<PostSummaryDTO> postSummaryList = followQueryService.getPostListByWriterId(writerId);

        assertNotNull(postSummaryList);
    }

}