package com.jamjam.bookjeok.domains.orderdetail.query.service;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.mapper.OrderDetailMapper;
import com.jamjam.bookjeok.exception.orderdetail.OrderDetailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailQueryServiceImpl implements OrderDetailQueryService {

    private final OrderDetailMapper orderDetailMapper;

    @Override
    public List<OrderDetailDTO> getOrderDetailByMemberUidAndOrderId(Long memberUid, String orderId) {
        List<OrderDetailDTO> orderDetails = orderDetailMapper.findOrderDetailByMemberUidAndOrderId(memberUid, orderId);

        if (orderDetails != null) {
            return orderDetails;
        }

        throw new OrderDetailNotFoundException("요청하신 주문 내역은 존재하지 않습니다.");
    }

}