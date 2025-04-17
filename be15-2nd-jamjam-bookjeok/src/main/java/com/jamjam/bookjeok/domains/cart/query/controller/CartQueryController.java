package com.jamjam.bookjeok.domains.cart.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.cart.command.dto.request.CartMemberIdRequest;
import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.cart.query.service.CartQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartQueryService cartQueryService;
    
    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<CartBookListResponse>> getBooksInCart(
            @RequestBody @Validated CartMemberIdRequest cartMemberIdRequest
    ) {
        CartBookListResponse cartBookListResponse = cartQueryService.getBooksInCart(cartMemberIdRequest.memberUid());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(cartBookListResponse));
    }

}