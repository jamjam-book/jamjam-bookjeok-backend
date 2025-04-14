package com.jamjam.bookjeok.domains.order.service.pendingorder;

import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.order.dto.pendingorder.response.PendingOrderResponse;
import com.jamjam.bookjeok.domains.order.entity.PendingOrder;
import com.jamjam.bookjeok.domains.order.repository.order.pendingorder.PendingOrderRepository;
import com.jamjam.bookjeok.domains.payment.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.exception.order.cart.CartItemLimitExceededException;
import com.jamjam.bookjeok.exception.payment.PaymentOrderNotFountException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PendingOrderServiceImplTest {

    @Autowired
    private PendingOrderService pendingOrderService;

    @Autowired
    private PendingOrderRepository pendingOrderRepository;

    private List<PendingOrderBookItemsRequest> pendingOrderBookItemsRequest;

    @BeforeEach
    void setUp() {
        pendingOrderBookItemsRequest = new ArrayList<>();
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(1L)
                .bookName("우리가 빛의 속도로 갈 수 없다면")
                .quantity(10)
                .totalPrice(210000)
                .build()
        );
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(12L)
                .bookName("7년의 밤")
                .quantity(3)
                .totalPrice(32700)
                .build()
        );
    }

    @Test
    @DisplayName("임시 주문 정보를 등록하는 테스트")
    void testCreateOrder() {
        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        PendingOrderResponse savedPendingOrder = pendingOrderService.createOrder(pendingOrderRequest);

        log.info("savedPendingOrder = {}", savedPendingOrder);

        assertThat(savedPendingOrder).isNotNull();
        assertThat(savedPendingOrder.orderId()).isNotEmpty();
        assertThat(savedPendingOrder.totalAmount()).isEqualTo(242700);
    }

    @Test
    @DisplayName("임시 주문 정보를 등록할 때 도서 정보가 없으면 예외가 발생하는 테스트")
    void testCreateOrderException() {
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(10L)
                .bookName("Clean Code")
                .quantity(1)
                .totalPrice(25000)
                .build()
        );

        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        assertThatThrownBy(() -> pendingOrderService.createOrder(pendingOrderRequest))
                .isInstanceOf(CartItemLimitExceededException.class)
                .hasMessage("존재하지 않는 도서 정보 입니다.");
    }

    @Test
    @DisplayName("임시 저장한 주문 정보를 가져오는 테스트")
    void testGetPendingOrder() {
        PendingOrderRequest pendingOrderRequest = PendingOrderRequest.builder()
                .memberUid(1L)
                .orderBookItems(pendingOrderBookItemsRequest)
                .build();

        PendingOrderResponse savedPendingOrder = pendingOrderService.createOrder(pendingOrderRequest);

        PendingOrder savePendingOrder = pendingOrderRepository.findPendingOrderByOrderIdAndTotalAmount(
                savedPendingOrder.orderId(), savedPendingOrder.totalAmount()
        ).get();

        PaymentConfirmRequest paymentConfirmRequest = PaymentConfirmRequest.builder()
                .orderId(savedPendingOrder.orderId())
                .amount(savedPendingOrder.totalAmount())
                .build();

        PendingOrder pendingOrder = pendingOrderService.getPendingOrder(paymentConfirmRequest);

        log.info("pendingOrder = {}", pendingOrder);

        assertThat(pendingOrder).isNotNull();
        assertThat(savePendingOrder.getMemberUid()).isEqualTo(pendingOrder.getMemberUid());
        assertThat(savePendingOrder.getOrderId()).contains(pendingOrder.getOrderId());
        assertThat(pendingOrder.getOrderData()).isEqualTo(pendingOrderBookItemsRequest);
    }

    @Test
    @DisplayName("임시 저장한 주문 정보가 없을 때 예외가 테스트")
    void testGetPendingOrderException() {
        PaymentConfirmRequest paymentConfirmRequest = PaymentConfirmRequest.builder()
                .orderId("2025041412345678")
                .amount(1000)
                .build();

        assertThatThrownBy(() -> pendingOrderService.getPendingOrder(paymentConfirmRequest))
                .isInstanceOf(PaymentOrderNotFountException.class)
                .hasMessage("주문 정보가 일치하지 않습니다.");
    }

}