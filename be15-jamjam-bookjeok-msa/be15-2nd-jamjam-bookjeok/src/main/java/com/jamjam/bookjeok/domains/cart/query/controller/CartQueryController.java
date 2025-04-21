package com.jamjam.bookjeok.domains.cart.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.cart.query.service.CartQueryService;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.query.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartQueryService cartQueryService;
    private final MemberQueryService memberQueryService;

    @GetMapping("/members/{memberId}/carts")
    public ResponseEntity<ApiResponse<CartBookListResponse>> getBooksInCart(
            @PathVariable(value = "memberId") String memberId
    ) {
        MemberDetailResponse memberDetail = memberQueryService.getMemberDetail(memberId);

        CartBookListResponse cartBookListResponse = cartQueryService.getBooksInCart(memberDetail.getMember().getMemberUid());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(cartBookListResponse));
    }

}