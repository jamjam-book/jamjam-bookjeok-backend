package com.jamjam.bookjeok.domains.cart.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.cart.dto.request.CartMemberIdRequest;
import com.jamjam.bookjeok.domains.cart.dto.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.cart.dto.response.CartResponse;
import com.jamjam.bookjeok.domains.cart.dto.request.CartRequest;
import com.jamjam.bookjeok.domains.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    
    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<CartBookListResponse>> getBooksInCart(
            @RequestBody @Validated CartMemberIdRequest cartMemberIdRequest
    ) {
        CartBookListResponse cartBookListResponse = cartService.getBooksInCart(cartMemberIdRequest.memberUid());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(cartBookListResponse));
    }

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<CartResponse>> createBookToCart(
            @RequestBody @Validated CartRequest cartRequest
    ) {
        CartResponse cartResponse = cartService.createBookToCart(cartRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(cartResponse));
    }

    @PutMapping("/cart")
    public ResponseEntity<ApiResponse<CartResponse>> modifyBookQuantity(
            @RequestBody @Validated CartRequest cartRequest
    ) {
        CartResponse cartResponse = cartService.modifyBookQuantity(cartRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(cartResponse));
    }

    @DeleteMapping("/cart")
    public ResponseEntity<ApiResponse<CartResponse>> deleteBookFromCartByMemberId(
            @RequestBody @Validated CartRequest cartRequest
    ) {
        cartService.deleteBookFromCartByMemberId(cartRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null));
    }

}