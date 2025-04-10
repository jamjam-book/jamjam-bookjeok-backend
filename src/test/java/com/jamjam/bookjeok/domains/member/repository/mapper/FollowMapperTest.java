package com.jamjam.bookjeok.domains.member.repository.mapper;

import com.jamjam.bookjeok.domains.member.dto.response.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.response.PostSummaryDTO;
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

    @DisplayName("멤버의 Uid로 팔로잉 리스트 가져오기")
    @Test
    void findFollowingListByMemberUidTest() {
        String id = "user01";

        List<FollowDTO> followingListDTO = followMapper.findFollowingListByMemberId(id);

        assertNotNull(followingListDTO);
        followingListDTO.forEach(System.out::println);
    }
}
