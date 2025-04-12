package com.jamjam.bookjeok.domains.member.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.domains.member.service.InterestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InterestBookController {

    private final InterestBookService interestBookService;

    @GetMapping("/{memberId}/interest-books")
    public ResponseEntity<ApiResponse<InterestBookListResponse>> getInterestBooListByMemberId(
            @PathVariable String memberId,
            @Validated PageRequest pageRequest
    ){
        InterestBookListResponse interestBookList =
                interestBookService.getInterestBookListByMemberId(memberId, pageRequest);

        return ResponseEntity.ok(ApiResponse.success(interestBookList));
    }

}