package com.jamjam.bookjeok.domains.member.query.mapper;

import com.jamjam.bookjeok.domains.member.command.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class AdminMapperTest {

    @Autowired
    private AdminMapper adminMapper;

    private PageRequest pageRequest;

    @DisplayName("findAllMember 테스트")
    @Test
    void findAllMemberTest(){
        pageRequest = new PageRequest(1,10);

        List<MemberDTO> members = adminMapper.findAllMember(pageRequest);

        assertNotNull(members);
        members.forEach(System.out::println);
    }

    @DisplayName("전체 멤버 인원 조회 테스트")
    @Test
    void countAllMember(){
        long count = adminMapper.countMembers();
        assertEquals(7, count);
    }


    @DisplayName("멤버의 아이디로 멤버를 검색하는 테스트")
    @Test
    void selectMemberByIdTest(){
        String memberId = "user02";

        MemberSearchRequest memberSearchRequest = new MemberSearchRequest(memberId,null);
        MemberDTO member
                = adminMapper.findMemberByIdOrNickname(memberSearchRequest);

        assertEquals("유형진", member.getMemberName());
    }

    @DisplayName("멤버의 닉네임으로 멤버를 검색하는 테스트")
    @Test
    void selectMemberByNicknameTest(){
        String nickname = "닉네임02";

        MemberSearchRequest memberSearchRequest = new MemberSearchRequest(null,nickname);
        MemberDTO member
                = adminMapper.findMemberByIdOrNickname(memberSearchRequest);

        assertEquals("유형진", member.getMemberName());
    }
}