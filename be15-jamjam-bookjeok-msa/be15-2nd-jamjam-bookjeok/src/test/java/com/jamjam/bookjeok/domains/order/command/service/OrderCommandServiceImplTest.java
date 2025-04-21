package com.jamjam.bookjeok.domains.order.command.service;

import com.jamjam.bookjeok.domains.order.command.dto.OrderResponse;
import com.jamjam.bookjeok.domains.order.command.dto.PageOrderResponse;
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

    private List<OrderResponse> orders;

    @BeforeEach
    void setUp() {
        orders = List.of(
                OrderResponse.builder()
                        .orderUid(56L)
                        .orderId("ORD001")
                        .orderName("도서 주문 1")
                        .totalAmount(25000)
                        .orderStatusName("결제승인")
                        .orderedAt(LocalDateTime.of(2025, 4, 9, 10, 0, 0))
                        .canceledAt(null)
                        .changedAt(null)
                        .refundedAt(null)
                        .build(),

                OrderResponse.builder()
                        .orderUid(57L)
                        .orderId("ORD002")
                        .orderName("도서 주문 2")
                        .totalAmount(31000)
                        .orderStatusName("결제승인")
                        .orderedAt(LocalDateTime.of(2025, 4, 10, 10, 0, 0))
                        .canceledAt(null)
                        .changedAt(null)
                        .refundedAt(null)
                        .build()
        );
    }

    @Test
    @DisplayName("회원uid 주문 목록을 조회하는 테스트")
    void testGetOrdersByMemberUid() {
        // given
        Long memberUid = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Page<OrderResponse> mockPage = new PageImpl<>(orders, pageable, orders.size());

        when(orderRepository.findAllOrdersByMemberUid(pageable, memberUid)).thenReturn(mockPage);

        // when
        PageOrderResponse pageOrderResponse = orderCommandService.getOrdersByMemberUid(pageable, memberUid);

        // then
        assertThat(pageOrderResponse).isNotNull();
        assertThat(pageOrderResponse.orders()).hasSize(2);
        assertThat(pageOrderResponse.pageNumber()).isEqualTo(0);
        assertThat(pageOrderResponse.pageSize()).isEqualTo(10);
        assertThat(pageOrderResponse.totalElements()).isEqualTo(2);
        assertThat(pageOrderResponse.totalPages()).isEqualTo(1);

        verify(orderRepository, times(1)).findAllOrdersByMemberUid(pageable, memberUid);
    }

}