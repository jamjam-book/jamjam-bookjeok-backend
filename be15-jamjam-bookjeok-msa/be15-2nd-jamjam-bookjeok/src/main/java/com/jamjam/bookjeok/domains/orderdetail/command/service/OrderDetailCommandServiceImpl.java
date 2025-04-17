package com.jamjam.bookjeok.domains.orderdetail.command.service;

import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.orderdetail.command.entity.OrderDetail;
import com.jamjam.bookjeok.domains.orderdetail.command.repository.OrderDetailRepository;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.mapper.OrderDetailMapper;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.exception.orderdetail.OrderDetailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailCommandServiceImpl implements OrderDetailCommandService {

    private final OrderDetailRepository orderDetailRepository;

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