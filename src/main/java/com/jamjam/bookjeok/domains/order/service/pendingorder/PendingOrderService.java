package com.jamjam.bookjeok.domains.order.service.pendingorder;

import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.response.PendingOrderResponse;
import com.jamjam.bookjeok.domains.order.entity.PendingOrder;
import com.jamjam.bookjeok.domains.payment.dto.request.PaymentConfirmRequest;

public interface PendingOrderService {

    PendingOrderResponse createOrder(PendingOrderRequest pendingOrderRequest);

    PendingOrder getPendingOrder(PaymentConfirmRequest paymentConfirmRequest);

}