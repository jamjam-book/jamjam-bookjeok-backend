package com.jamjam.bookjeok.domains.cart.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.cart.command.service.CartCommandService;
import com.jamjam.bookjeok.domains.cart.command.dto.request.CartRequest;
import com.jamjam.bookjeok.domains.cart.command.dto.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartCommandController {

    private final CartCommandService cartCommandService;

    @PostMapping("/carts")
    public ResponseEntity<ApiResponse<CartResponse>> createBookToCart(
            @RequestBody @Validated CartRequest cartRequest
    ) {
        CartResponse cartResponse = cartCommandService.createBookToCart(cartRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(cartResponse));
    }

    @PutMapping("/carts")
    public ResponseEntity<ApiResponse<CartResponse>> modifyBookQuantity(
            @RequestBody @Validated CartRequest cartRequest
    ) {
        CartResponse cartResponse = cartCommandService.modifyBookQuantity(cartRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(cartResponse));
    }

    @DeleteMapping("/carts")
    public ResponseEntity<ApiResponse<CartResponse>> deleteBookFromCartByMemberId(
            @RequestBody @Validated CartRequest cartRequest
    ) {
        cartCommandService.deleteBookFromCartByMemberId(cartRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null));
    }

}