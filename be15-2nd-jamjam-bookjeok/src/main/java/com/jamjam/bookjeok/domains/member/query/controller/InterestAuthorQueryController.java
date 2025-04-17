package com.jamjam.bookjeok.domains.member.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.query.dto.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.query.service.InterestAuthorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InterestAuthorQueryController {

    private final InterestAuthorQueryService interestAuthorQueryService;

    @GetMapping("/{memberId}/interest-authors")
    public ResponseEntity<ApiResponse<List<InterestAuthorDTO>>> getInterestAuthorByMemberId(
            @PathVariable String memberId
    ){

        List<InterestAuthorDTO> interestAuthorList
                = interestAuthorQueryService.getInterestAuthorList(memberId);

        return ResponseEntity.ok(ApiResponse.success(interestAuthorList));

    }

}
