package com.jamjam.bookjeok.domains.orderdetail.service;

import com.jamjam.bookjeok.domains.orderdetail.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.pendingorder.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.order.entity.Order;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailDTO> findOrderDetailByOrderId(String orderId);

    void createOrderDetails(List<PendingOrderBookItemsRequest> orderItems, Order order);

}