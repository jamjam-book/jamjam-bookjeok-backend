package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.exception.member.followException.AlreadyFollowException;
import com.jamjam.bookjeok.exception.member.followException.NotFollowException;
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
class FollowCommandServiceImplTest {

    @Autowired
    private FollowCommandService followCommandService;

    @DisplayName("팔로잉 테스트")
    @Test
    void createFollowTest() {
        String followerId = "user01";
        String followingId = "user04";

        String response = followCommandService.createFollow(followingId, followerId);

        assertEquals("닉네임04", response);
    }

    @DisplayName("이미 팔로우 목록에 있는 경우 예외 발생 시키기")
    @Test
    void alreadyFollowExceptionTest() {
        String followerId = "user01";
        String followingId = "user02";

        assertThrows(AlreadyFollowException.class, () -> {
            followCommandService.createFollow(followingId, followerId);
        });
    }

    @DisplayName("팔로우 취소 테스트")
    @Test
    void deleteFollowTest(){
        String followingId = "user02";
        String followerId = "user01";

        followCommandService.deleteFollow(followingId, followerId);

        // 팔로우가 취소 됐으므로
        assertThrows(NotFollowException.class, () ->
                followCommandService.deleteFollow(followingId, followerId));
    }

}