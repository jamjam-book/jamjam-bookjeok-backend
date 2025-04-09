package com.jamjam.bookjeok.domains.order.controller.cart;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.order.dto.cart.response.CartResponse;
import com.jamjam.bookjeok.domains.order.dto.cart.request.CartRequest;
import com.jamjam.bookjeok.domains.order.service.cart.CartService;
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

}