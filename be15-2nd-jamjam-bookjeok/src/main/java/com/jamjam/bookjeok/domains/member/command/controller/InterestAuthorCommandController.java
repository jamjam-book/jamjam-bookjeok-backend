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

    @PostMapping("/interest-author")
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

    @DeleteMapping("/interest-author")
    public ResponseEntity<ApiResponse<Void>> deleteInterestAuthor(
            @RequestBody @Validated InterestAuthorRequest request
    ){
        interestAuthorCommandService.deleteInterestAuthor(request);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
