package com.jamjam.bookjeok.domains.member.query.service;

import com.jamjam.bookjeok.domains.member.command.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberListResponse;
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
        MemberSearchRequest memberSearchRequest = new MemberSearchRequest(1, 10, null, null);

        MemberDTO member1 = MemberDTO.builder()
                .memberUid(1L)
                .memberId("user01")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .email("test1@gmail.com")
                .nickname("닉네임01")
                .marketingConsent(true)
                .createdAt(LocalDateTime.of(2025, 4, 6, 11, 13, 40))
                .activityStatus("ACTIVE")
                .build();

        MemberDTO member2 = MemberDTO.builder()
                .memberUid(2L)
                .memberId("user02")
                .memberName("유형진")
                .phoneNumber("01024001349")
                .email("test2@gmail.com")
                .nickname("닉네임02")
                .marketingConsent(true)
                .createdAt(LocalDateTime.of(2025, 2, 6, 14, 13, 32))
                .activityStatus("DEACTIVATE")
                .build();

        List<MemberDTO> fakeMembers = Arrays.asList(member1, member2);

        when(adminMapper.findAllMember(memberSearchRequest)).thenReturn(fakeMembers);
        when(adminMapper.countMembers(memberSearchRequest)).thenReturn(2L);

        MemberListResponse response = adminQueryService.getAllMembers(memberSearchRequest);

        assertNotNull(response);
        assertEquals(2, response.getMemberList().size());
    }

    @DisplayName("getMemberById 서비스 단위 테스트")
    @Test
    void getMemberByIdTest() {
        MemberSearchRequest memberSearchRequest = new MemberSearchRequest(1, 10, "user02", null);

        MemberDTO member = MemberDTO.builder()
                .memberUid(1L)
                .memberId("user01")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .email("test1@gmail.com")
                .nickname("닉네임01")
                .marketingConsent(true)
                .createdAt(LocalDateTime.of(2025, 4, 6, 11, 13, 40))
                .activityStatus("ACTIVE")
                .build();

        List<MemberDTO> fakeMembers = Arrays.asList(member);

        when(adminMapper.findAllMember(memberSearchRequest)).thenReturn(fakeMembers);

        MemberListResponse result = adminQueryService.getAllMembers(memberSearchRequest);

        assertNotNull(result);
        assertEquals("닉네임01", result.getMemberList().get(0).getNickname());
    }

    @DisplayName("getMemberByNickname 서비스 단위 테스트")
    @Test
    void getMemberByIdOrNicknameTest() {
        MemberSearchRequest memberSearchRequest = new MemberSearchRequest(1, 10, null, "닉네임01");
        MemberDTO member = MemberDTO.builder()
                .memberUid(1L)
                .memberId("user01")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .email("test1@gmail.com")
                .nickname("닉네임01")
                .marketingConsent(true)
                .createdAt(LocalDateTime.of(2025, 4, 6, 11, 13, 40))
                .activityStatus("ACTIVE")
                .build();

        List<MemberDTO> fakeMembers = Arrays.asList(member);

        when(adminMapper.findAllMember(memberSearchRequest)).thenReturn(fakeMembers);

        MemberListResponse result = adminQueryService.getAllMembers(memberSearchRequest);

        assertNotNull(result);
        assertEquals("홍길동", result.getMemberList().get(0).getMemberName());
    }

    @DisplayName("회원이 없는 경우 exception 발생")
    @Test
    void getMemberByIdOrNicknameExceptionTest() {
        String memberId = "fakeId";

        when(adminMapper.findMemberByMemberId(memberId)).thenReturn(null);

        assertThrows(MemberException.class,
                () -> adminQueryService.getMemberByMemberId(memberId));
    }
}
