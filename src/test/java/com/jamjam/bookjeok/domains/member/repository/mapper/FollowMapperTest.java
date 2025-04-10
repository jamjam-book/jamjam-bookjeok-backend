package com.jamjam.bookjeok.domains.member.repository.mapper;

import com.jamjam.bookjeok.domains.member.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.PostSummaryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class FollowMapperTest {

    @Autowired
    private FollowMapper followMapper;

    @DisplayName("멤버의 id로 팔로잉 리스트 가져오기")
    @Test
    void findFollowingListByMemberUidTest() {
        String id = "user01";

        List<FollowDTO> followingListDTO = followMapper.findFollowingListByMemberId(id);

        assertNotNull(followingListDTO);
        followingListDTO.forEach(System.out::println);
    }

    @DisplayName("멤버의 id로 게시글 목록 가져오기")
    @Test
    void findPostListByMemberIdTest(){
        String id = "user02";

        List<PostSummaryDTO> postSummaryList = followMapper.findPostListByMemberId(id);

        assertNotNull(postSummaryList);
    }

}
