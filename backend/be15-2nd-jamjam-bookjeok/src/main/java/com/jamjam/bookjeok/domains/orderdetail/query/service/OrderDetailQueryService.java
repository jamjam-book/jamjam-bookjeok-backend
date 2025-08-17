package com.jamjam.bookjeok.domains.orderdetail.query.service;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.response.OrderDetailResponse;

public interface OrderDetailQueryService {

    OrderDetailResponse getOrderDetailByMemberUidAndOrderId(Long memberUid, String orderId);

}