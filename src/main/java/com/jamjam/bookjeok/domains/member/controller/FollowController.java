package com.jamjam.bookjeok.domains.member.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.dto.response.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.response.PostSummaryDTO;
import com.jamjam.bookjeok.domains.member.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
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

}
