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
        PageRequest request = new PageRequest(1, 10);

        MemberDTO member1 = MemberDTO.builder()
                .memberUid(1L)
                .memberId("user01")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .email("test1@gmail.com")
                .nickname("닉네임01")
                .birthDate("19970816")
                .marketingConsent(true)
                .role(MemberRole.MEMBER)
                .createdAt(LocalDateTime.of(2025, 4, 6, 11, 13, 40))
                .modifiedAt(LocalDateTime.of(2025, 4, 7, 11, 30, 40))
                .activityStatus("ACTIVE")
                .build();

    MemberDTO member2 = MemberDTO.builder()
            .memberUid(2L)
            .memberId("user02")
            .memberName("유형진")
            .phoneNumber("01024001349")
            .email("test2@gmail.com")
            .nickname("닉네임02")
            .birthDate("19970918")
            .marketingConsent(true)
            .role(MemberRole.MEMBER)
            .createdAt(LocalDateTime.of(2025, 2, 6, 14, 13, 32))
            .modifiedAt(null)
            .activityStatus("DEACTIVATE")
            .build();

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
        MemberDTO member = MemberDTO.builder()
                .memberUid(1L)
                .memberId("user01")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .email("test1@gmail.com")
                .nickname("닉네임01")
                .birthDate("19970816")
                .marketingConsent(true)
                .role(MemberRole.MEMBER)
                .createdAt(LocalDateTime.of(2025, 4, 6, 11, 13, 40))
                .modifiedAt(LocalDateTime.of(2025, 4, 7, 11, 30, 40))
                .activityStatus("ACTIVE")
                .build();


        when(adminMapper.findMemberByIdOrNickname(searchRequest)).thenReturn(member);

        MemberDetailResponse result = adminQueryService.getMemberByIdOrNickname(searchRequest);

        assertNotNull(result);
        assertEquals("홍길동", result.getMember().getMemberName());
    }

    @DisplayName("getMemberByNickname 서비스 단위 테스트")
    @Test
    void getMemberByIdOrNicknameTest() {
        MemberSearchRequest searchRequest = new MemberSearchRequest(null, "닉네임01");
        MemberDTO member = MemberDTO.builder()
                .memberUid(1L)
                .memberId("user01")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .email("test1@gmail.com")
                .nickname("닉네임01")
                .birthDate("19970816")
                .marketingConsent(true)
                .role(MemberRole.MEMBER)
                .createdAt(LocalDateTime.of(2025, 4, 6, 11, 13, 40))
                .modifiedAt(LocalDateTime.of(2025, 4, 7, 11, 30, 40))
                .activityStatus("ACTIVE")
                .build();

        when(adminMapper.findMemberByIdOrNickname(searchRequest)).thenReturn(member);

        MemberDetailResponse result = adminQueryService.getMemberByIdOrNickname(searchRequest);

        assertNotNull(result);
        assertEquals("닉네임01", result.getMember().getNickname());
    }

    @DisplayName("아무것도 검색하지 않는 경우에 exception 발생")
    @Test
    void getMemberByIdOrNicknameExceptionTest() {
        MemberSearchRequest searchRequest = new MemberSearchRequest(null, null);
        MemberDTO member = MemberDTO.builder()
                .memberUid(1L)
                .memberId("user01")
                .memberName("홍길동")
                .phoneNumber("01012345678")
                .email("test1@gmail.com")
                .nickname("닉네임01")
                .birthDate("19970816")
                .marketingConsent(true)
                .role(MemberRole.MEMBER)
                .createdAt(LocalDateTime.of(2025, 4, 6, 11, 13, 40))
                .modifiedAt(LocalDateTime.of(2025, 4, 7, 11, 30, 40))
                .activityStatus("ACTIVE")
                .build();

        when(adminMapper.findMemberByIdOrNickname(searchRequest)).thenReturn(member);

        assertThrows(MemberException.class,
                () -> adminQueryService.getMemberByIdOrNickname(searchRequest));
    }
}
