package com.jamjam.bookjeok.domains.order.service.orderdetail;

import com.jamjam.bookjeok.domains.order.dto.orderdetail.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailDTO> findOrderDetailByOrderId(String orderId);

}