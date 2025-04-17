package com.jamjam.bookjeok.domains.pendingorder.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.pendingorder.command.service.PendingOrderCommandService;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.response.PendingOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PendingOrderController {

    private final PendingOrderCommandService pendingOrderCommandService;

    @PostMapping("/order/payment")
    public ResponseEntity<ApiResponse<PendingOrderResponse>> createOrder(
            @RequestBody @Validated PendingOrderRequest pendingOrderRequest
    ) {
        PendingOrderResponse pendingOrderResponse = pendingOrderCommandService.createOrder(pendingOrderRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(pendingOrderResponse));
    }

}