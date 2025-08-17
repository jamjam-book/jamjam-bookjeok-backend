package com.jamjam.bookjeok.domains.pendingorder.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.response.PendingOrderResponse;
import com.jamjam.bookjeok.domains.pendingorder.command.service.PendingOrderCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PendingOrderCommandController {

    private final PendingOrderCommandService pendingOrderCommandService;

    @PostMapping("/pending-order")
    public ResponseEntity<ApiResponse<PendingOrderResponse>> createOrder(
            @RequestBody @Validated PendingOrderRequest pendingOrderRequest
    ) {
        PendingOrderResponse pendingOrderResponse = pendingOrderCommandService.createOrder(pendingOrderRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(pendingOrderResponse));
    }

    @DeleteMapping("/pending-order/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deletePendingOrder(
            @PathVariable(value = "orderId") String orderId
    ) {
        pendingOrderCommandService.deletePendingOrder(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(null));
    }

}