package com.jamjam.bookjeok.domains.member.query.mapper;

import com.jamjam.bookjeok.domains.member.command.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AdminMapperTest {

    @Autowired
    private AdminMapper adminMapper;

    private MemberSearchRequest memberSearchRequest;

    @DisplayName("findAllMember 테스트")
    @Test
    void findAllMemberTest(){
        memberSearchRequest = new MemberSearchRequest(1, 10, null, null);

        List<MemberDTO> members = adminMapper.findAllMember(memberSearchRequest);

        assertNotNull(members);
        members.forEach(System.out::println);
    }

    @DisplayName("전체 멤버 인원 조회 테스트")
    @Test
    void countAllMember(){
        memberSearchRequest = new MemberSearchRequest(1, 10, null, null);

        long count = adminMapper.countMembers(memberSearchRequest);
        assertEquals(7, count);
    }


    @DisplayName("멤버의 아이디로 멤버를 검색하는 테스트")
    @Test
    void selectMemberByIdTest(){
        String memberId = "user02";

        memberSearchRequest = new MemberSearchRequest(1, 10, memberId, null);
        List<MemberDTO> member = adminMapper.findAllMember(memberSearchRequest);

        assertEquals("닉네임02", member.get(0).getNickname());
    }

    @DisplayName("멤버의 닉네임으로 멤버를 검색하는 테스트")
    @Test
    void selectMemberByNicknameTest(){
        String nickname = "닉네임02";

        memberSearchRequest = new MemberSearchRequest(1, 10, null, nickname);
        List<MemberDTO> member = adminMapper.findAllMember(memberSearchRequest);

        assertEquals("유형진", member.get(0).getMemberName());
    }

    @DisplayName("멤버의 아이디로 멤버를 조회하는 테스트")
    @Test
    void selectMemberByMemberIdTest(){
        String memberId = "user02";

        MemberDTO member = adminMapper.findMemberByMemberId(memberId);

        assertEquals("닉네임02", member.getNickname());
    }
}