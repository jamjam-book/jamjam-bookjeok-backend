package com.jamjam.bookjeok.domains.orderdetail.query.service;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.mapper.OrderDetailMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDetailQueryServiceImplTest {

    @InjectMocks
    private OrderDetailQueryServiceImpl orderDetailQueryService;

    @Mock
    private OrderDetailMapper orderDetailMapper;

    private List<OrderDetailDTO> orderDetails;

    @BeforeEach
    void setUp() {
        orderDetails = List.of(
                OrderDetailDTO.builder()
                        .orderId("ORD001")
                        .bookName("책이름01")
                        .isbn("9781082502299")
                        .totalPrice(15000)
                        .quantity(1)
                        .imageUrl("http://localhost:8080/productimgs/604248.png")
                        .orderedAt(LocalDateTime.now().withNano(0))
                        .build()
        );
    }

    @Test
    @DisplayName("회원Uid와 주문id로 주문 상세 내역을 조회하는 테스트")
    void testGetOrderDetailByOrderId() {
        Long memberUid = 1L;
        String orderId = "ORD001";

        when(orderDetailMapper.findOrderDetailByOrderId(memberUid, orderId)).thenReturn(orderDetails);

        List<OrderDetailDTO> orderDetailsResult = orderDetailQueryService.getOrderDetailByOrderId(memberUid, orderId);

        assertNotNull(orderDetailsResult);
        assertThat(orderDetailsResult).hasSize(1);
        assertThat(orderDetailsResult.get(0).orderId()).isEqualTo("ORD001");
        assertThat(orderDetailsResult.get(0).bookName()).isEqualTo("책이름01");
        assertThat(orderDetailsResult.get(0).isbn()).isEqualTo("9781082502299");
        assertThat(orderDetailsResult.get(0).totalPrice()).isEqualTo(15000);
        assertThat(orderDetailsResult.get(0).quantity()).isEqualTo(1);
        assertThat(orderDetailsResult.get(0).imageUrl()).isEqualTo("http://localhost:8080/productimgs/604248.png");
        assertThat(orderDetailsResult.get(0).orderedAt()).isNotNull();
    }

}