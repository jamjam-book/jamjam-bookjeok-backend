package com.jamjam.bookjeok.domains.payment.command.controller;

import com.jamjam.bookjeok.common.dto.ApiResponse;
import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.domains.payment.command.dto.response.PaymentConfirmResponse;
import com.jamjam.bookjeok.domains.payment.command.service.PaymentCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentCommandController {

    private final PaymentCommandService paymentCommandService;

    @PostMapping("/payment/{paymentKey}/confirm")
    public ResponseEntity<ApiResponse<PaymentConfirmResponse>> confirmPayment(
            @PathVariable(value = "paymentKey") String paymentKey,
            @RequestBody @Validated PaymentConfirmRequest paymentConfirmRequest
    ) {
        PaymentConfirmResponse paymentConfirmResponse = paymentCommandService.confirmPayment(paymentKey, paymentConfirmRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(paymentConfirmResponse));
    }

}