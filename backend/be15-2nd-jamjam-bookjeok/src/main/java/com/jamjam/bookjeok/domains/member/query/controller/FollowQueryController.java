package com.jamjam.bookjeok.domains.member.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.query.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.query.dto.PostSummaryDTO;
import com.jamjam.bookjeok.domains.member.query.service.FollowQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class FollowQueryController {

    private final FollowQueryService followQueryService;

    @GetMapping("/members/{memberId}/followings")
    public ResponseEntity<ApiResponse<List<FollowDTO>>> getFollowListByMemberUid(
            @PathVariable String memberId
    ){
        List<FollowDTO> followList = followQueryService.getFollowingListByMemberId(memberId);

        return ResponseEntity.ok(ApiResponse.success(followList));
    }

    @GetMapping("/{writerId}/posts")
    public ResponseEntity<ApiResponse<List<PostSummaryDTO>>> getPostListByWriterId(
            @PathVariable String writerId
    ){
        List<PostSummaryDTO> postList = followQueryService.getPostListByWriterId(writerId);

        return ResponseEntity.ok(ApiResponse.success(postList));

    }
}
