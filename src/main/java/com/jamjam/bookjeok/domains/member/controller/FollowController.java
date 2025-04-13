package com.jamjam.bookjeok.domains.member.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.PostSummaryDTO;
import com.jamjam.bookjeok.domains.member.dto.request.FollowMemberRequest;
import com.jamjam.bookjeok.domains.member.dto.response.FollowMemberResponse;
import com.jamjam.bookjeok.domains.member.service.FollowService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class FollowController {

    private final FollowService followService;

    @GetMapping("/{memberId}/followings")
    public ResponseEntity<ApiResponse<List<FollowDTO>>> getFollowListByMemberUid(
            @PathVariable String memberId
    ){
        List<FollowDTO> followList = followService.getFollowingListByMemberId(memberId);

        return ResponseEntity.ok(ApiResponse.success(followList));
    }

    @GetMapping("/{writerId}/postList")
    public ResponseEntity<ApiResponse<List<PostSummaryDTO>>> getPostListByWriterId(
            @PathVariable String writerId
    ){
        List<PostSummaryDTO> postList = followService.getPostListByWriterId(writerId);

        return ResponseEntity.ok(ApiResponse.success(postList));

    }

    // 로그인 끝나면 이 부분 수정하기
    @PostMapping("/{followerId}/follow")
    public ResponseEntity<ApiResponse<FollowMemberResponse>> createFollow(
            @RequestBody @Validated FollowMemberRequest followMemberRequest,
            @PathVariable String followerId
    ){

        String followingId = followMemberRequest.getFollowingId();

        log.info("followerId : {}", followerId);
        log.info("followingId : {}", followingId);

        String nickname = followService.createFollow(followingId, followerId);

        FollowMemberResponse response = FollowMemberResponse.builder()
                .nickname(nickname)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
}
