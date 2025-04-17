package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberListResponse;
import com.jamjam.bookjeok.domains.member.command.entity.MemberRole;
import com.jamjam.bookjeok.domains.member.query.dto.MemberDTO;
import com.jamjam.bookjeok.domains.member.query.mapper.AdminMapper;
import com.jamjam.bookjeok.exception.member.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminMapper adminMapper;

    @InjectMocks
    private AdminQueryServiceImpl adminQueryService;


    @DisplayName("findAllMember 서비스 단위 테스트")
    @Test
    void getAllMemberTest() {
        PageRequest request = new PageRequest(1,10);

        MemberDTO member1 = new MemberDTO(
                1L,
                "user01",
                "홍길동",
                "01012345678",
                "test1@gmail.com",
                "닉네임01",
                "19970816",
                true,
                MemberRole.MEMBER,
                LocalDateTime.of(2025, 4, 6, 11, 13, 40),
                LocalDateTime.of(2025, 4, 7, 11, 30, 40),
                "ACTIVE"
        );

        MemberDTO member2 = new MemberDTO(
                2L,
                "user02",
                "유형진",
                "01024001349",
                "test2@gmail.com",
                "닉네임02",
                "19970918",
                true,
                MemberRole.MEMBER,
                LocalDateTime.of(2025, 2, 6, 14, 13, 32),
                null,
                "DEACTIVATE"
        );


        List<MemberDTO> fakeMembers = Arrays.asList(member1, member2);

        when(adminMapper.findAllMember(request)).thenReturn(fakeMembers);
        when(adminMapper.countMembers()).thenReturn(2L);

        MemberListResponse response = adminQueryService.getAllMembers(request);

        assertNotNull(response);
        assertEquals(2, response.getMemberList().size());
    }

    @DisplayName("getMemberById 서비스 단위 테스트")
    @Test
    void getMemberByIdTest() {
        MemberSearchRequest searchRequest = new MemberSearchRequest("user02", null);
        MemberDTO member = new MemberDTO(
                1L,
                "user01",
                "정유진",
                "01012345678",
                "test1@gmail.com",
                "닉네임01",
                "19970816",
                true,
                MemberRole.MEMBER,
                LocalDateTime.of(2025, 4, 6, 11, 13, 40),
                LocalDateTime.of(2025, 4, 7, 11, 30, 40),
                "ACTIVE"
        );

        when(adminMapper.findMemberByIdOrNickname(searchRequest)).thenReturn(member);

        MemberDetailResponse result = adminQueryService.getMemberByIdOrNickname(searchRequest);

        assertNotNull(result);
        assertEquals("정유진", result.getMember().getMemberName());
    }

    @DisplayName("getMemberByNickname 서비스 단위 테스트")
    @Test
    void getMemberByIdOrNicknameTest() {
        MemberSearchRequest searchRequest = new MemberSearchRequest(null, "닉네임01");
        MemberDTO member = new MemberDTO(
                1L,
                "user01",
                "정유진",
                "01012345678",
                "test1@gmail.com",
                "닉네임01",
                "19970816",
                true,
                MemberRole.MEMBER,
                LocalDateTime.of(2025, 4, 6, 11, 13, 40),
                LocalDateTime.of(2025, 4, 7, 11, 30, 40),
                "ACTIVE"
        );

        when(adminMapper.findMemberByIdOrNickname(searchRequest)).thenReturn(member);

        MemberDetailResponse result = adminQueryService.getMemberByIdOrNickname(searchRequest);

        assertNotNull(result);
        assertEquals("닉네임01", result.getMember().getNickname());
    }

    @DisplayName("아무것도 검색하지 않는 경우에 exception 발생")
    @Test
    void getMemberByIdOrNicknameExceptionTest() {
        MemberSearchRequest searchRequest = new MemberSearchRequest(null, null);
        MemberDTO member = new MemberDTO(
                1L,
                "user01",
                "정유진",
                "01012345678",
                "test1@gmail.com",
                "닉네임01",
                "19970816",
                true,
                MemberRole.MEMBER,
                LocalDateTime.of(2025, 4, 6, 11, 13, 40),
                LocalDateTime.of(2025, 4, 7, 11, 30, 40),
                "ACTIVE"
        );

        when(adminMapper.findMemberByIdOrNickname(searchRequest)).thenReturn(member);

        assertThrows(MemberException.class,
                () -> adminQueryService.getMemberByIdOrNickname(searchRequest));
    }
}
