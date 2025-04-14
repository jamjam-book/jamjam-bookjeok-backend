package com.jamjam.bookjeok.domains.order.service.order;

import com.jamjam.bookjeok.domains.order.entity.Order;
import com.jamjam.bookjeok.domains.order.entity.PendingOrder;
import com.jamjam.bookjeok.domains.order.repository.order.OrderRepository;
import com.jamjam.bookjeok.domains.payment.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(PendingOrder findPendingOrder, PaymentDTO paymentDTO) {
        Integer paymentApproveOrderStatusId = 1;

        Order order = Order.builder()
                .memberUid(findPendingOrder.getMemberUid())
                .orderStatusId(paymentApproveOrderStatusId)
                .orderId(paymentDTO.orderId())
                .orderName(paymentDTO.orderName())
                .totalAmount(paymentDTO.totalAmount())
                .build();
        return orderRepository.save(order);
    }

}