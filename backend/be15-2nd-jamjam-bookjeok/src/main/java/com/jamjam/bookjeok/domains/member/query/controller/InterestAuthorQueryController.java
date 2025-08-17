package com.jamjam.bookjeok.domains.member.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestAuthorListResponse;
import com.jamjam.bookjeok.domains.member.query.service.InterestAuthorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InterestAuthorQueryController {

    private final InterestAuthorQueryService interestAuthorQueryService;

    @GetMapping("/members/{memberId}/interest/authors")
    public ResponseEntity<ApiResponse<InterestAuthorListResponse>> getInterestAuthorByMemberId(
            @PathVariable String memberId,
            @Validated PageRequest pageRequest
    ){

        InterestAuthorListResponse interestAuthorList
                = interestAuthorQueryService.getInterestAuthorList(memberId, pageRequest);

        return ResponseEntity.ok(ApiResponse.success(interestAuthorList));
    }

}
