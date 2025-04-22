package com.jamjam.bookjeok.domains.orderdetail.query.mapper;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    List<OrderDetailDTO> findOrderDetailByMemberUidAndOrderId(Long memberUid, String orderId);

}