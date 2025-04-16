package com.jamjam.bookjeok.domains.pendingorder.service;

import com.jamjam.bookjeok.domains.pendingorder.dto.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.pendingorder.dto.response.PendingOrderResponse;
import com.jamjam.bookjeok.domains.pendingorder.entity.PendingOrder;
import com.jamjam.bookjeok.domains.payment.dto.request.PaymentConfirmRequest;

public interface PendingOrderService {

    PendingOrderResponse createOrder(PendingOrderRequest pendingOrderRequest);

    PendingOrder getPendingOrder(PaymentConfirmRequest paymentConfirmRequest);

    void deletePendingOrder(String orderId);

}