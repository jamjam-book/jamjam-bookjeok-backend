package com.jamjam.bookjeok.domains.order.command.service;

import com.jamjam.bookjeok.domains.order.command.dto.OrderResponse;
import com.jamjam.bookjeok.domains.order.command.dto.PageOrderResponse;
import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.order.command.repository.OrderRepository;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;

    @Override
    public PageOrderResponse getOrdersByMemberUid(Pageable pageable, Long memberUid) {
        Page<OrderResponse> pageOrderResponse = orderRepository.findAllOrdersByMemberUid(pageable, memberUid);
        return toPageOrderResponse(pageOrderResponse);
    }

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

    private PageOrderResponse toPageOrderResponse(Page<OrderResponse> pageOrderResponse) {
        return PageOrderResponse.builder()
                .orders(pageOrderResponse.getContent())
                .pageNumber(pageOrderResponse.getNumber())
                .pageSize(pageOrderResponse.getSize())
                .totalElements(pageOrderResponse.getTotalElements())
                .totalPages(pageOrderResponse.getTotalPages())
                .build();
    }

}