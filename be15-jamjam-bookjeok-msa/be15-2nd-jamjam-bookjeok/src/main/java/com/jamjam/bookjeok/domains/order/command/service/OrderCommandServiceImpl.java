package com.jamjam.bookjeok.domains.order.command.service;

import com.jamjam.bookjeok.domains.order.command.dto.OrderDTO;
import com.jamjam.bookjeok.domains.order.command.dto.OrderItemDTO;
import com.jamjam.bookjeok.domains.order.command.dto.response.OrderResponse;
import com.jamjam.bookjeok.domains.order.command.dto.response.PageOrderResponse;
import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.order.command.repository.OrderRepository;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;

    @Override
    public PageOrderResponse getOrdersByMemberUid(Pageable pageable, Long memberUid) {
        Page<String> orderIdsPage = orderRepository.findOrderIdsByMemberUid(pageable, memberUid);
        List<String> orderIds = orderIdsPage.getContent();

        List<OrderResponse> flatList = orderRepository.findOrderResponsesByOrderIds(orderIds);
        List<OrderDTO> groupedOrders = groupOrdersByOrderId(flatList);

        return PageOrderResponse.builder()
                .orders(groupedOrders)
                .pageNumber(orderIdsPage.getNumber())
                .pageSize(orderIdsPage.getSize())
                .totalElements(orderIdsPage.getTotalElements())
                .totalPages(orderIdsPage.getTotalPages())
                .build();
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

    private List<OrderDTO> groupOrdersByOrderId(List<OrderResponse> flatList) {
        return flatList.stream()
                .collect(Collectors.groupingBy(OrderResponse::orderId))
                .entrySet()
                .stream()
                .map(this::mapToOrderDTO)
                .sorted(Comparator.comparing(OrderDTO::orderedAt).reversed())
                .toList();
    }

    private OrderDTO mapToOrderDTO(Map.Entry<String, List<OrderResponse>> entry) {
        String orderId = entry.getKey();
        List<OrderResponse> responses = entry.getValue();
        OrderResponse firstOrder = responses.get(0);

        List<OrderItemDTO> items = responses.stream()
                .map(this::mapToOrderItemDTO)
                .toList();

        return OrderDTO.builder()
                .orderId(orderId)
                .orderedAt(firstOrder.orderedAt())
                .orderStatusName(firstOrder.orderStatusName())
                .items(items)
                .build();
    }

    private OrderItemDTO mapToOrderItemDTO(OrderResponse response) {
        return OrderItemDTO.builder()
                .bookName(response.bookName())
                .quantity(response.quantity())
                .totalPrice(response.totalPrice())
                .imageUrl(response.imageUrl())
                .build();
    }

}