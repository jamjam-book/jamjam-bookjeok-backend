package com.jamjam.bookjeok.domains.orderdetail.command.service;

import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;

import java.util.List;

public interface OrderDetailCommandService {

    void createOrderDetails(List<PendingOrderBookItemsRequest> orderItems, Order order);

}