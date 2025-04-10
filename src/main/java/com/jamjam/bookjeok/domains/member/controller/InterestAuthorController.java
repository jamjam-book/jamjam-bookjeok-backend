package com.jamjam.bookjeok.domains.member.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.dto.response.InterestAuthorDTO;
import com.jamjam.bookjeok.domains.member.service.InterestAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InterestAuthorController {

    private final InterestAuthorService interestAuthorService;

    @GetMapping("{memberId}/interest-authors")
    public ResponseEntity<ApiResponse<List<InterestAuthorDTO>>> getInterestAuthorByMemberId(
            @PathVariable String memberId
    ){

        List<InterestAuthorDTO> interestAuthorList
                = interestAuthorService.getInterestAuthorList(memberId);

        return ResponseEntity.ok(ApiResponse.success(interestAuthorList));

    }
}
