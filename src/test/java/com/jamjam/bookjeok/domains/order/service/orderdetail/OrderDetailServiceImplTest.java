package com.jamjam.bookjeok.domains.order.service.orderdetail;

import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.order.entity.Order;
import com.jamjam.bookjeok.domains.order.entity.OrderDetail;
import com.jamjam.bookjeok.domains.order.repository.order.OrderRepository;
import com.jamjam.bookjeok.domains.order.repository.orderdetail.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class OrderDetailServiceImplTest {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    private List<PendingOrderBookItemsRequest> orderItems;
    private Order order;

    @BeforeEach
    void setUp() {
        orderItems = new ArrayList<>();
        orderItems.add(PendingOrderBookItemsRequest.builder()
                        .bookId(1L)
                        .bookName("우리가 빛의 속도로 갈 수 없다면")
                        .quantity(10)
                        .totalPrice(210000)
                .build());

        order = Order.builder()
                .orderId(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                        UUID.randomUUID().toString()
                                .replace("-", "")
                                .substring(0, 8)
                )
                .orderName("주문 테스트")
                .totalAmount(210000)
                .orderedAt(LocalDateTime.now().withNano(0))
                .build();
    }

    @Test
    @DisplayName("주문 상세 내역을 저장하는 테스트")
    void testCreateOrderDetails() {
        Order saveOrder = orderRepository.save(order);

        orderDetailService.createOrderDetails(orderItems, saveOrder);
        List<OrderDetail> orderDetail = orderDetailRepository.findOrderDetailByOrderUid(saveOrder.getOrderUid());

        assertThat(orderDetail.size()).isEqualTo(1);
        assertThat(orderDetail.get(0).getOrderUid()).isEqualTo(saveOrder.getOrderUid());
        assertThat(orderDetail.get(0).getBookId()).isEqualTo(orderItems.get(0).bookId());
        assertThat(orderDetail.get(0).getQuantity()).isEqualTo(orderItems.get(0).quantity());
        assertThat(orderDetail.get(0).getTotalPrice()).isEqualTo(orderItems.get(0).totalPrice());
    }

}