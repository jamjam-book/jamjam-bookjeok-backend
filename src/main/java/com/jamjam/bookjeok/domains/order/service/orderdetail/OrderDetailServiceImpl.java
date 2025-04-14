package com.jamjam.bookjeok.domains.order.service.orderdetail;

import com.jamjam.bookjeok.domains.order.dto.orderdetail.OrderDetailDTO;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.order.entity.Order;
import com.jamjam.bookjeok.domains.order.entity.OrderDetail;
import com.jamjam.bookjeok.domains.order.repository.orderdetail.OrderDetailRepository;
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
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetailDTO> findOrderDetailByOrderId(String orderId) {
        List<OrderDetailDTO> orderDetails = orderDetailMapper.findOrderDetailByOrderId(orderId);

        if (orderDetails != null) {
            return orderDetails;
        }

        throw new OrderDetailNotFoundException("요청하신 주문 내역은 존재하지 않습니다.");
    }

    @Override
    public void createOrderDetails(List<PendingOrderBookItemsRequest> orderItems, Order order) {
        for (PendingOrderBookItemsRequest item : orderItems) {
            OrderDetail detail = OrderDetail.builder()
                    .orderUid(order.getOrderUid())
                    .bookId(item.bookId())
                    .quantity(item.quantity())
                    .totalPrice(item.totalPrice())
                    .build();
            orderDetailRepository.save(detail);
        }
    }

}