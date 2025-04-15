package com.jamjam.bookjeok.domains.member.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.dto.request.MemberSearchRequest;
import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.dto.response.MemberListResponse;
import com.jamjam.bookjeok.domains.member.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

//    @GetMapping("/admin/members")
//    public ResponseEntity<ApiResponse<MemberListResponse>> getAllMembers(
//            @Validated PageRequest pageRequest
//    ){
//        MemberListResponse response = adminService.getAllMembers(pageRequest);
//
//        return ResponseEntity.ok(ApiResponse.success(response));
//    }

    @GetMapping("/admin/member")
    public ResponseEntity<ApiResponse<MemberDetailResponse>> getMemberByName(
            MemberSearchRequest memberSearchRequest
    ){

        MemberDetailResponse response = adminService.getMemberByIdOrNickname(memberSearchRequest);

        log.info("멤버의 아이디 = " + response.getMember().getMemberId());
        log.info("멤버의 닉네임 = " + response.getMember().getNickname());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
