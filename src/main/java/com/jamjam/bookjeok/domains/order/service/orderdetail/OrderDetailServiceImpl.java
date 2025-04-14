package com.jamjam.bookjeok.domains.order.service.orderdetail;

import com.jamjam.bookjeok.domains.order.dto.orderdetail.OrderDetailDTO;
import com.jamjam.bookjeok.domains.order.repository.orderdetail.mapper.OrderDetailMapper;
import com.jamjam.bookjeok.exception.order.orderdetail.OrderDetailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailMapper orderDetailMapper;

    @Override
    public List<OrderDetailDTO> findOrderDetailByOrderId(String orderId) {
        List<OrderDetailDTO> orderDetails = orderDetailMapper.findOrderDetailByOrderId(orderId);

        if (orderDetails != null) {
            return orderDetails;
        }

        throw new OrderDetailNotFoundException("요청하신 주문 내역은 존재하지 않습니다.");
    }

}