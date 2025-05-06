package com.jamjam.bookjeok.domains.orderdetail.query.service;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.response.OrderDetailResponse;
import com.jamjam.bookjeok.domains.orderdetail.query.mapper.OrderDetailMapper;
import com.jamjam.bookjeok.domains.payment.query.dto.PaymentDetailDTO;
import com.jamjam.bookjeok.domains.payment.query.service.PaymentDetailService;
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

    @Mock
    private PaymentDetailService paymentDetailService;

    private List<OrderDetailDTO> books;

    private PaymentDetailDTO paymentDetail;

    @BeforeEach
    void setUp() {
        books = List.of(
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

        paymentDetail = PaymentDetailDTO.builder()
                .totalAmount(15000)
                .paymentMethod("토스페이")
                .build();
    }

    @Test
    @DisplayName("회원Uid와 주문id로 주문 상세 내역을 조회하는 테스트")
    void testGetOrderDetailByMemberUidAndOrderId() {
        Long memberUid = 1L;
        String orderId = "ORD001";
        Long paymentId = 1L;

        when(orderDetailMapper.findOrderDetailByMemberUidAndOrderId(memberUid, orderId)).thenReturn(books);
        when(paymentDetailService.getPaymentId(orderId)).thenReturn(paymentId);
        when(paymentDetailService.getPaymentDetail(paymentId)).thenReturn(paymentDetail);

        OrderDetailResponse orderDetailResponse = orderDetailQueryService.getOrderDetailByMemberUidAndOrderId(memberUid, orderId);

        assertNotNull(orderDetailResponse);
        assertThat(orderDetailResponse.orderId()).isEqualTo("ORD001");
        assertThat(orderDetailResponse.books().get(0).bookName()).isEqualTo("책이름01");
        assertThat(orderDetailResponse.books().get(0).isbn()).isEqualTo("9781082502299");
        assertThat(orderDetailResponse.books().get(0).totalPrice()).isEqualTo(15000);
        assertThat(orderDetailResponse.books().get(0).quantity()).isEqualTo(1);
        assertThat(orderDetailResponse.books().get(0).imageUrl()).isEqualTo("http://localhost:8080/productimgs/604248.png");
        assertThat(orderDetailResponse.paymentDetail().totalAmount()).isEqualTo(15000);
        assertThat(orderDetailResponse.paymentDetail().paymentMethod()).isEqualTo("토스페이");
        assertThat(orderDetailResponse.orderedAt()).isNotNull();
    }

}