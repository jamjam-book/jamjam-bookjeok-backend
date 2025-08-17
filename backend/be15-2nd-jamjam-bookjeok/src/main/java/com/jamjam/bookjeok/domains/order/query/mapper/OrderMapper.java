package com.jamjam.bookjeok.domains.order.query.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    Long findOrderUidByOrderId(String orderId);

    Long findPaymentIdByOrderUid(Long orderUid);

}