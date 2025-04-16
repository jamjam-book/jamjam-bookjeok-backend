package com.jamjam.bookjeok.domains.order.controller.order;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.response.PendingOrderResponse;
import com.jamjam.bookjeok.domains.order.service.pendingorder.PendingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PendingOrderController {

    private final PendingOrderService pendingOrderService;

    @PostMapping("/pending-order")
    public ResponseEntity<ApiResponse<PendingOrderResponse>> createOrder(
            @RequestBody @Validated PendingOrderRequest pendingOrderRequest
    ) {
        PendingOrderResponse pendingOrderResponse = pendingOrderService.createOrder(pendingOrderRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(pendingOrderResponse));
    }

    @DeleteMapping("/pending-order/{order-id}")
    public ResponseEntity<ApiResponse<Void>> deletePendingOrder(
            @PathVariable("order-id") String orderId
    ) {
        pendingOrderService.deletePendingOrder(orderId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(null));
    }

}