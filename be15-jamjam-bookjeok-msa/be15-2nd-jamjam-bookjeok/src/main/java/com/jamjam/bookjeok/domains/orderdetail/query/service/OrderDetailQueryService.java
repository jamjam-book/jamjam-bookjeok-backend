package com.jamjam.bookjeok.domains.orderdetail.query.service;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailQueryService {

    List<OrderDetailDTO> getOrderDetailByMemberUidAndOrderId(Long memberUid, String orderId);

}