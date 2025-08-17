package com.jamjam.bookjeok.domains.pendingorder.command.service;

import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.response.PendingOrderResponse;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;

public interface PendingOrderCommandService {

    PendingOrderResponse createOrder(PendingOrderRequest pendingOrderRequest);

    PendingOrder getPendingOrder(PaymentConfirmRequest paymentConfirmRequest);

    void deletePendingOrder(String orderId);

}