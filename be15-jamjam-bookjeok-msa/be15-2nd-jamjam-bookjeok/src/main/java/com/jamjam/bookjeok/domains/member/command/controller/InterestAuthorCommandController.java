package com.jamjam.bookjeok.domains.member.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.service.InterestAuthorCommandService;
import com.jamjam.bookjeok.domains.member.command.dto.request.InterestAuthorRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestAuthorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InterestAuthorCommandController {

    private final InterestAuthorCommandService interestAuthorCommandService;

    @PostMapping("/members/interest/authors")
    public ResponseEntity<ApiResponse<InterestAuthorResponse>> createInterestAuthor(
            @RequestBody @Validated InterestAuthorRequest request
    ){
        String authorName = interestAuthorCommandService.createInterestAuthor(request);

        InterestAuthorResponse response = InterestAuthorResponse.builder()
                .authorName(authorName)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/members/{memberId}/interest/authors/{authorId}")
    public ResponseEntity<ApiResponse<Void>> deleteInterestAuthor(
            @PathVariable String memberId,
            @PathVariable Long authorId

    ){
        interestAuthorCommandService.deleteInterestAuthor(memberId, authorId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
