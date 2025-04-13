package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.PostSummaryDTO;
import com.jamjam.bookjeok.exception.member.followException.AlreadyFollowException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @DisplayName("멤버의 id 통해 팔로우 목록 가져오기")
    @Test
    void findFollowingListByMemberId() {
        String memberId = "user01";

        List<FollowDTO> followList = followService.getFollowingListByMemberId(memberId);

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

        List<PostSummaryDTO> postSummaryList = followService.getPostListByWriterId(writerId);

        assertNotNull(postSummaryList);
    }

    @DisplayName("팔로잉 테스트")
    @Test
    void createFollowTest() {
        String followerId = "user01";
        String followingId = "user04";

        String response = followService.createFollow(followingId, followerId);

        assertEquals("닉네임04", response);
    }

    @DisplayName("이미 팔로우 목록에 있는 경우 예외 발생 시키기")
    @Test
    void alreadyFollowExceptionTest() {
        String followerId = "user01";
        String followingId = "user02";

        assertThrows(AlreadyFollowException.class, () -> {
            followService.createFollow(followingId, followerId);
        });
    }

}