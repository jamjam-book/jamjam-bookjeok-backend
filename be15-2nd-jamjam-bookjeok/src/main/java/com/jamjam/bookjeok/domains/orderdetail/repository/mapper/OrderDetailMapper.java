package com.jamjam.bookjeok.domains.orderdetail.repository.mapper;

import com.jamjam.bookjeok.domains.orderdetail.dto.OrderDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    List<OrderDetailDTO> findOrderDetailByOrderId(String orderDetailId);

}