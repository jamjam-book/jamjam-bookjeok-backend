package com.jamjam.bookjeok.domains.member.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.service.FollowCommandService;
import com.jamjam.bookjeok.domains.member.command.dto.request.FollowMemberRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.FollowMemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class FollowCommandController {

    private final FollowCommandService followCommandService;

    // 로그인 끝나면 이 부분 수정하기
    @PostMapping("/{followerId}/follow")
    public ResponseEntity<ApiResponse<FollowMemberResponse>> createFollow(
            @RequestBody @Validated FollowMemberRequest followMemberRequest,
            @PathVariable String followerId
    ){

        String followingId = followMemberRequest.getFollowingId();

        log.info("followerId : {}", followerId);
        log.info("followingId : {}", followingId);

        String nickname = followCommandService.createFollow(followingId, followerId);

        FollowMemberResponse response = FollowMemberResponse.builder()
                .nickname(nickname)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{followerId}/follow")
    public ResponseEntity<ApiResponse<Void>> deleteFollow(
            @RequestBody @Validated FollowMemberRequest followMemberRequest,
            @PathVariable String followerId
    ){
        String followingId = followMemberRequest.getFollowingId();

        log.info("followingId : {}", followingId);
        log.info("followerId : {}", followerId);

        followCommandService.deleteFollow(followingId, followerId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
