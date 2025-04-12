package com.jamjam.bookjeok.domains.order.controller.order;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.response.PendingOrderResponse;
import com.jamjam.bookjeok.domains.order.service.pendingorder.PendingOrderService;
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

    private final PendingOrderService pendingOrderService;

    @PostMapping("/order/payment")
    public ResponseEntity<ApiResponse<PendingOrderResponse>> createOrder(
            @RequestBody @Validated PendingOrderRequest pendingOrderRequest
    ) {
        PendingOrderResponse pendingOrderResponse = pendingOrderService.createOrder(pendingOrderRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(pendingOrderResponse));
    }

}