package com.jamjam.bookjeok.domains.payment.command.service;

import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.repository.BookRepository;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.orderdetail.command.entity.OrderDetail;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;

import com.jamjam.bookjeok.domains.order.command.repository.OrderRepository;
import com.jamjam.bookjeok.domains.pendingorder.command.repository.PendingOrderRepository;
import com.jamjam.bookjeok.domains.orderdetail.command.repository.OrderDetailRepository;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.service.OrderDetailQueryService;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.domains.payment.command.dto.response.PaymentConfirmResponse;
import com.jamjam.bookjeok.domains.payment.command.entity.Payment;
import com.jamjam.bookjeok.domains.payment.command.infrastructure.service.TossPaymentService;
import com.jamjam.bookjeok.domains.payment.command.repository.PaymentRepository;

import com.jamjam.bookjeok.exception.payment.BookInfoNotFoundException;
import com.jamjam.bookjeok.exception.payment.InsufficientBookStockException;
import com.jamjam.bookjeok.exception.payment.PaymentOrderNotFountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final TossPaymentService tossPaymentService;
    private final OrderDetailQueryService orderDetailQueryService;

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final PendingOrderRepository pendingOrderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentConfirmResponse confirmPayment(PaymentConfirmRequest paymentConfirmRequest) {
        PendingOrder findPendingOrder = validatePendingOrder(paymentConfirmRequest);
        List<PendingOrderBookItemsRequest> orderItems = validateBookStocks(findPendingOrder);
        PaymentDTO paymentDTO = tossPaymentService.approvePayment(paymentConfirmRequest);
        Order savedOrder = saveOrder(findPendingOrder, paymentDTO);
        saveOrderDetails(orderItems, savedOrder);
        List<OrderDetailDTO> orderDetails = orderDetailQueryService.findOrderDetailByOrderId(paymentDTO.orderId());
        savePayment(paymentDTO, savedOrder);
        pendingOrderRepository.delete(findPendingOrder);

        return PaymentConfirmResponse.builder()
                .orderDetails(orderDetails)
                .build();
    }

    private PendingOrder validatePendingOrder(PaymentConfirmRequest paymentConfirmRequest) {
        return pendingOrderRepository.findPendingOrderByOrderIdAndTotalAmount(paymentConfirmRequest.orderId(), paymentConfirmRequest.amount())
                .orElseThrow(() -> new PaymentOrderNotFountException("주문 정보가 일치하지 않아 결제를 완료할 수 없습니다."));
    }

    private List<PendingOrderBookItemsRequest> validateBookStocks(PendingOrder findPendingOrder) {
        List<PendingOrderBookItemsRequest> orderDataList = findPendingOrder.getOrderData();

        orderDataList.forEach(orderData -> {
            Book findBook = bookRepository.findBookByBookId(orderData.bookId())
                    .orElseThrow(() -> new BookInfoNotFoundException("도서 정보를 찾을 수 없습니다."));

            if (findBook.getStockQuantity() < orderData.quantity()) {
                pendingOrderRepository.delete(findPendingOrder);
                throw new InsufficientBookStockException("도서 수량이 부족하여 결제를 진행할 수 없습니다.");
            }
        });
        return orderDataList;
    }

    private Order saveOrder(PendingOrder findPendingOrder, PaymentDTO paymentDTO) {
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

    private void saveOrderDetails(List<PendingOrderBookItemsRequest> orderItems, Order order) {
        for (PendingOrderBookItemsRequest item : orderItems) {
            OrderDetail detail = OrderDetail.builder()
                    .orderUid(order.getOrderUid())
                    .bookId(item.bookId())
                    .quantity(item.quantity())
                    .totalPrice(item.totalPrice())
                    .build();
            orderDetailRepository.save(detail);
        }
    }

    private void savePayment(PaymentDTO paymentDTO, Order order) {
        Payment payment = Payment.builder()
                .orderUid(order.getOrderUid())
                .paymentKey(paymentDTO.paymentKey())
                .paymentType(paymentDTO.type())
                .paymentMethod(paymentDTO.method())
                .totalAmount(paymentDTO.totalAmount())
                .requestedAt(OffsetDateTime.parse(paymentDTO.requestedAt()).toLocalDateTime())
                .approvedAt(OffsetDateTime.parse(paymentDTO.approvedAt()).toLocalDateTime())
                .build();
        paymentRepository.save(payment);
    }

}