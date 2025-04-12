package com.jamjam.bookjeok.domains.order.service.pendingorder;

import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.response.PendingOrderResponse;

public interface PendingOrderService {

    PendingOrderResponse createOrder(PendingOrderRequest pendingOrderRequest);

}