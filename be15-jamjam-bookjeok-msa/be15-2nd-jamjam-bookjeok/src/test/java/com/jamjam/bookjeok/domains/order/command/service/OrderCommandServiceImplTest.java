package com.jamjam.bookjeok.domains.order.command.service;

import com.jamjam.bookjeok.domains.order.command.dto.response.OrderResponse;
import com.jamjam.bookjeok.domains.order.command.dto.response.PageOrderResponse;
import com.jamjam.bookjeok.domains.order.command.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCommandServiceImplTest {

    @InjectMocks
    private OrderCommandServiceImpl orderCommandService;

    @Mock
    private OrderRepository orderRepository;

    private List<OrderResponse> orderResponses;

    @BeforeEach
    void setUp() {
        orderResponses = List.of(
                OrderResponse.builder()
                        .orderUid(1L)
                        .memberUid(1L)
                        .orderId("ORD001")
                        .bookName("책1")
                        .quantity(2)
                        .totalPrice(20000)
                        .orderStatusName("결제승인")
                        .orderedAt(LocalDateTime.of(2025, 4, 9, 10, 0, 0))
                        .imageUrl("http://localhost:8080/book1.png")
                        .build(),

                OrderResponse.builder()
                        .orderUid(1L)
                        .memberUid(1L)
                        .orderId("ORD001")
                        .bookName("책2")
                        .quantity(1)
                        .totalPrice(15000)
                        .orderStatusName("결제승인")
                        .orderedAt(LocalDateTime.of(2025, 4, 9, 10, 0, 0))
                        .imageUrl("http://localhost:8080/book2.png")
                        .build(),

                OrderResponse.builder()
                        .orderUid(2L)
                        .memberUid(1L)
                        .orderId("ORD002")
                        .bookName("책3")
                        .quantity(1)
                        .totalPrice(12000)
                        .orderStatusName("결제승인")
                        .orderedAt(LocalDateTime.of(2025, 4, 10, 10, 0, 0))
                        .imageUrl("http://localhost:8080/book3.png")
                        .build()
        );
    }

    @Test
    @DisplayName("회원uid 주문 목록을 조회하는 테스트")
    void testGetOrdersByMemberUid() {
        // given
        Long memberUid = 1L;
        Pageable pageable = PageRequest.of(0, 5);
        List<String> orderIds = List.of("ORD001", "ORD002");

        Page<String> mockOrderIdPage = new PageImpl<>(orderIds, pageable, orderIds.size());
        when(orderRepository.findOrderIdsByMemberUid(pageable, memberUid)).thenReturn(mockOrderIdPage);
        when(orderRepository.findOrderResponsesByOrderIds(orderIds)).thenReturn(orderResponses);

        // when
        PageOrderResponse result = orderCommandService.getOrdersByMemberUid(pageable, memberUid);

        // then
        assertThat(result).isNotNull();
        assertThat(result.orders()).hasSize(2);
        assertThat(result.orders().get(0).items()).hasSize(1);
        assertThat(result.orders().get(1).items()).hasSize(2);
        assertThat(result.pageNumber()).isEqualTo(0);
        assertThat(result.pageSize()).isEqualTo(5);
        assertThat(result.totalElements()).isEqualTo(2);
        assertThat(result.totalPages()).isEqualTo(1);

        verify(orderRepository).findOrderIdsByMemberUid(pageable, memberUid);
        verify(orderRepository).findOrderResponsesByOrderIds(orderIds);
    }

}