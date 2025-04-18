package com.jamjam.bookjeok.domains.pendingorder.command.entity;

import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PendingOrderTest {

    private List<PendingOrderBookItemsRequest> pendingOrderBookItemsRequest;

    @BeforeEach
    void setUp() {
        pendingOrderBookItemsRequest = new ArrayList<>();

        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(1L)
                .bookName("태백산맥 1권")
                .quantity(10)
                .totalPrice(180000)
                .build()
        );
        pendingOrderBookItemsRequest.add(PendingOrderBookItemsRequest.builder()
                .bookId(5L)
                .bookName("7년의 밤")
                .quantity(3)
                .totalPrice(48000)
                .build()
        );
    }

    @Test
    @DisplayName("주문ID를 생성하는 메서드 테스트")
    void testCreateOrderId() {
        final String DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        PendingOrder pendingOrder = PendingOrder.builder()
                .memberUid(1L)
                .totalAmount(228000)
                .orderData(pendingOrderBookItemsRequest)
                .build();

        log.info("orderId = {}", pendingOrder.getOrderId());

        assertThat(pendingOrder.getOrderId()).isNotNull();
        assertThat(pendingOrder.getOrderId()).contains(DATE);
    }

}