package com.jamjam.bookjeok.domains.member.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.domains.member.query.service.InterestBookQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InterestBookQueryController {

    private final InterestBookQueryService interestBookQueryService;

    @GetMapping("/{memberId}/interest-books")
    public ResponseEntity<ApiResponse<InterestBookListResponse>> getInterestBooListByMemberId(
            @PathVariable String memberId,
            @Validated PageRequest pageRequest
    ){
        InterestBookListResponse interestBookList =
                interestBookQueryService.getInterestBookListByMemberId(memberId, pageRequest);

        return ResponseEntity.ok(ApiResponse.success(interestBookList));
    }

}