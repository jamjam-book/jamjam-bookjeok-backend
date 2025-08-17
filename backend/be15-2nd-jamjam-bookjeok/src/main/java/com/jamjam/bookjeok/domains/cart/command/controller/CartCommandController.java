package com.jamjam.bookjeok.domains.cart.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.cart.command.service.CartCommandService;
import com.jamjam.bookjeok.domains.cart.command.dto.request.CartRequest;
import com.jamjam.bookjeok.domains.cart.command.dto.response.CartResponse;
import com.jamjam.bookjeok.domains.member.command.dto.response.MemberDetailResponse;
import com.jamjam.bookjeok.domains.member.query.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartCommandController {

    private final MemberQueryService memberQueryService;
    private final CartCommandService cartCommandService;

    @PostMapping("/members/{memberId}/carts")
    public ResponseEntity<ApiResponse<CartResponse>> createBookToCart(
            @PathVariable(value = "memberId") String memberId,
            @RequestBody @Validated CartRequest cartRequest
    ) {
        MemberDetailResponse memberDetail = memberQueryService.getMemberDetail(memberId);
        Long memberUid = memberDetail.getMember().getMemberUid();

        CartResponse cartResponse = cartCommandService.createBookToCart(memberUid, cartRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(cartResponse));
    }

    @PutMapping("/members/{memberId}/carts")
    public ResponseEntity<ApiResponse<CartResponse>> modifyBookQuantity(
            @PathVariable(value = "memberId") String memberId,
            @RequestBody @Validated CartRequest cartRequest
    ) {
        MemberDetailResponse memberDetail = memberQueryService.getMemberDetail(memberId);
        Long memberUid = memberDetail.getMember().getMemberUid();

        CartResponse cartResponse = cartCommandService.modifyBookQuantity(memberUid, cartRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(cartResponse));
    }

    @DeleteMapping("/members/{memberId}/carts")
    public ResponseEntity<ApiResponse<CartResponse>> deleteBookFromCartByMemberId(
            @PathVariable(value = "memberId") String memberId,
            @RequestBody @Validated CartRequest cartRequest
    ) {
        MemberDetailResponse memberDetail = memberQueryService.getMemberDetail(memberId);
        Long memberUid = memberDetail.getMember().getMemberUid();

        cartCommandService.deleteBookFromCartByMemberId(memberUid, cartRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null));
    }

}