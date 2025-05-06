package com.jamjam.bookjeok.domains.payment.command.service;

import com.jamjam.bookjeok.domains.book.command.service.BookStockCommandService;
import com.jamjam.bookjeok.domains.order.command.service.OrderCommandService;
import com.jamjam.bookjeok.domains.orderdetail.command.service.OrderDetailCommandService;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.response.OrderDetailResponse;
import com.jamjam.bookjeok.domains.payment.command.entity.Payment;
import com.jamjam.bookjeok.domains.payment.command.dto.TossPaymentApproveRequest;
import com.jamjam.bookjeok.domains.payment.query.service.PaymentDetailService;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;

import com.jamjam.bookjeok.domains.orderdetail.query.service.OrderDetailQueryService;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.domains.payment.command.dto.response.PaymentConfirmResponse;
import com.jamjam.bookjeok.domains.payment.command.infrastructure.service.TossPaymentCommandService;

import com.jamjam.bookjeok.domains.pendingorder.command.service.PendingOrderCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 결제 커맨드 처리를 담당하는 서비스 클래스입니다.
 * 이 서비스는 결제 승인 과정을 조율합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PendingOrderCommandService pendingOrderCommandService;
    private final TossPaymentCommandService tossPaymentCommandService;
    private final OrderCommandService orderCommandService;
    private final OrderDetailCommandService orderDetailCommandService;
    private final OrderDetailQueryService orderDetailQueryService;
    private final BookStockCommandService bookStockCommandService;
    private final PaymentEntityCommandService paymentEntityService;
    private final PaymentDetailService paymentDetailService;

    /**
     * 결제 요청을 처리하고, 재고를 검증한 후 주문을 생성하며,
     * 결제 정보를 저장하고 주문 상세 정보를 반환합니다.
     *
     * @param paymentKey 결제 서비스에서 전달받은 결제 키
     * @param paymentConfirmRequest 결제 승인 요청 정보
     * @return 주문 상세 정보가 포함된 결제 승인 응답
     */
    @Override
    public PaymentConfirmResponse confirmPayment(String paymentKey, PaymentConfirmRequest paymentConfirmRequest) {
        // 보류 중인 주문 조회
        PendingOrder pendingOrder = pendingOrderCommandService.getPendingOrder(paymentConfirmRequest);

        // 토스 결제 승인 요청
        TossPaymentApproveRequest tossPaymentApproveRequest = createTossPaymentApproveRequest(paymentKey, pendingOrder);
        PaymentDTO paymentDTO = tossPaymentCommandService.approvePayment(tossPaymentApproveRequest);

        // 결제 승인 후 재고 감소
        List<PendingOrderBookItemsRequest> orderItems = bookStockCommandService.validateAndUpdateBookStocks(pendingOrder);

        // 주문 및 주문 상세 생성
        Order savedOrder = orderCommandService.createOrder(pendingOrder, paymentDTO);
        orderDetailCommandService.createOrderDetails(orderItems, savedOrder);

        // 결제 정보 저장 및 보류 주문 삭제
        Payment savedPayment = paymentEntityService.createPayment(paymentDTO, savedOrder);
        pendingOrderCommandService.deletePendingOrder(pendingOrder.getOrderId());

        // 주문 상세 정보 조회
        OrderDetailResponse orderDetailResponse = orderDetailQueryService.getOrderDetailByMemberUidAndOrderId(
                savedOrder.getMemberUid(), paymentDTO.orderId()
        );

        return PaymentConfirmResponse.builder()
                .orderDetails(orderDetailResponse)
                .build();
    }

    /**
     * 결제 키와 보류 주문 정보를 기반으로 토스 결제 승인 요청 객체를 생성합니다.
     *
     * @param paymentKey 결제 서비스에서 전달받은 결제 키
     * @param pendingOrder 보류 중인 주문 정보
     * @return 토스 결제 승인 요청 객체
     */
    private TossPaymentApproveRequest createTossPaymentApproveRequest(String paymentKey, PendingOrder pendingOrder) {
        return TossPaymentApproveRequest.builder()
                .paymentKey(paymentKey)
                .orderId(pendingOrder.getOrderId())
                .amount(pendingOrder.getTotalAmount())
                .build();
    }

}