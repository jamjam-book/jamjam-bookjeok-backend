package com.jamjam.bookjeok.domains.pendingorder.command.service;

import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.response.PendingOrderResponse;

public interface PendingOrderCommandService {

    PendingOrderResponse createOrder(PendingOrderRequest pendingOrderRequest);

}