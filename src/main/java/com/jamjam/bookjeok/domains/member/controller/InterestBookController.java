package com.jamjam.bookjeok.domains.member.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.member.dto.request.InterestAuthorRequest;
import com.jamjam.bookjeok.domains.member.dto.request.InterestBookRequest;
import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.response.InterestAuthorResponse;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookResponse;
import com.jamjam.bookjeok.domains.member.entity.InterestBook;
import com.jamjam.bookjeok.domains.member.service.InterestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/interest-book")
    public ResponseEntity<ApiResponse<InterestBookResponse>> createInterestAuthor(
            @RequestBody @Validated InterestBookRequest request
    ){

        Long memberUid = 1L;

        String bookName = interestBookService.createInterestBook(memberUid, request);

        InterestBookResponse response = InterestBookResponse.builder()
                .bookName(bookName)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

}