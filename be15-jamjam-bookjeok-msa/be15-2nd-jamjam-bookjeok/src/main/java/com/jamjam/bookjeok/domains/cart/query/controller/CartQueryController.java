package com.jamjam.bookjeok.domains.cart.query.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.cart.query.dto.response.CartBookListResponse;
import com.jamjam.bookjeok.domains.cart.query.service.CartQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartQueryController {

    private final CartQueryService cartQueryService;

    @GetMapping("/carts/{memberUid}")
    public ResponseEntity<ApiResponse<CartBookListResponse>> getBooksInCart(
            @PathVariable(value = "memberUid") Long memberUid
    ) {
        CartBookListResponse cartBookListResponse = cartQueryService.getBooksInCart(memberUid);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(cartBookListResponse));
    }

}