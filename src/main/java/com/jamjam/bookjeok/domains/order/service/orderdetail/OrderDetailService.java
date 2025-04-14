package com.jamjam.bookjeok.domains.order.service.orderdetail;

import com.jamjam.bookjeok.domains.order.dto.orderdetail.OrderDetailDTO;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.order.entity.Order;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailDTO> findOrderDetailByOrderId(String orderId);

    void createOrderDetails(List<PendingOrderBookItemsRequest> orderItems, Order order);

}