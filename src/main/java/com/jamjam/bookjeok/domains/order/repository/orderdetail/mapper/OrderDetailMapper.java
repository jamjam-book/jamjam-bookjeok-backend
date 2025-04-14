package com.jamjam.bookjeok.domains.order.repository.orderdetail.mapper;

import com.jamjam.bookjeok.domains.order.dto.orderdetail.OrderDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    List<OrderDetailDTO> findOrderDetailByOrderId(String orderDetailId);

}