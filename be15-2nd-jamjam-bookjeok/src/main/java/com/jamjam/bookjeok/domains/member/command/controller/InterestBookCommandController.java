package com.jamjam.bookjeok.domains.member.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.command.service.InterestBookCommandService;
import com.jamjam.bookjeok.domains.member.command.dto.request.InterestBookRequest;
import com.jamjam.bookjeok.domains.member.command.dto.response.InterestBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InterestBookCommandController {

    private final InterestBookCommandService interestBookCommandService;

    @PostMapping("/interest-book")
    public ResponseEntity<ApiResponse<InterestBookResponse>> createInterestBook(
            @RequestBody @Validated InterestBookRequest request
    ){
        Long memberUid = 1L;

        String bookName = interestBookCommandService.createInterestBook(memberUid, request);

        InterestBookResponse response = InterestBookResponse.builder()
                .bookName(bookName)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/interest-book")
    public ResponseEntity<ApiResponse<Void>> deleteInterestBook(
            @RequestBody @Validated InterestBookRequest request
    ){
        // 로그인 연결 후 수정
        Long memberUid = 1L;

        interestBookCommandService.deleteInterestBook(memberUid, request);

        return ResponseEntity.ok(ApiResponse.success(null));
    }

}