package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.response.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.response.PostSummaryDTO;
import com.jamjam.bookjeok.domains.member.repository.mapper.FollowMapper;
import com.jamjam.bookjeok.exception.member.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowMapper followMapper;

    @InjectMocks
    private FollowService followService;

    @DisplayName("멤버의 id 통해 팔로우 목록 가져오기")
    @Test
    void findFollowingListByMemberId() {
        // given
        String memberID = "user01";
        List<FollowDTO> mockFollowList = List.of(
                new FollowDTO("user02", "닉네임02"),
                new FollowDTO("user03", "닉네임03")
        );

        when(followMapper.findFollowingListByMemberId(memberID)).thenReturn(mockFollowList);

        // when
        List<FollowDTO> followList = followService.getFollowingListByMemberId(memberID);

        // then
        assertNotNull(followList);
        assertEquals(2, followList.size());
        assertEquals("user02", followList.get(0).getMemberId());
        assertEquals("닉네임02", followList.get(0).getNickname());
        assertEquals("user03", followList.get(1).getMemberId());
        assertEquals("닉네임03", followList.get(1).getNickname());

        followList.forEach(System.out::println);
    }


    @DisplayName("팔로우한 사용자의 게시글 목록 정상 조회")
    @Test
    void getPostsByWriterIdTest() {
        String writerId = "user02";

        List<PostSummaryDTO> mockPostList = List.of(
                PostSummaryDTO.builder()
                        .nickname("닉네임02")
                        .title("제목1")
                        .build(),
                PostSummaryDTO.builder()
                        .nickname("닉네임02")
                        .title("제목2")
                        .build()
        );


        when(followMapper.findPostListByMemberId(writerId)).thenReturn(mockPostList);

        List<PostSummaryDTO> postList = followService.getPostListByWriterId(writerId);

        assertNotNull(postList);
    }
}