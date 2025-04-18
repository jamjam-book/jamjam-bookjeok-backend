package com.jamjam.bookjeok.domains.payment.command.service;

import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.repository.BookRepository;
import com.jamjam.bookjeok.domains.order.command.service.OrderCommandService;
import com.jamjam.bookjeok.domains.orderdetail.command.service.OrderDetailCommandService;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.service.OrderDetailQueryService;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.domains.payment.command.dto.response.PaymentConfirmResponse;
import com.jamjam.bookjeok.domains.payment.command.entity.Payment;
import com.jamjam.bookjeok.domains.payment.command.infrastructure.service.TossPaymentCommandService;
import com.jamjam.bookjeok.domains.payment.command.repository.PaymentRepository;

import com.jamjam.bookjeok.domains.pendingorder.command.service.PendingOrderCommandService;
import com.jamjam.bookjeok.exception.payment.BookInfoNotFoundException;
import com.jamjam.bookjeok.exception.payment.InsufficientBookStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PendingOrderCommandService pendingOrderService;
    private final TossPaymentCommandService tossPaymentCommandService;
    private final OrderCommandService orderCommandService;
    private final OrderDetailCommandService orderDetailService;
    private final OrderDetailQueryService orderDetailQueryService;

    private final BookRepository bookRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentConfirmResponse confirmPayment(PaymentConfirmRequest paymentConfirmRequest) {
        PendingOrder findPendingOrder = pendingOrderService.getPendingOrder(paymentConfirmRequest);
        List<PendingOrderBookItemsRequest> orderItems = validateBookStocks(findPendingOrder);
        PaymentDTO paymentDTO = tossPaymentCommandService.approvePayment(paymentConfirmRequest);
        Order savedOrder = orderCommandService.createOrder(findPendingOrder, paymentDTO);
        orderDetailService.createOrderDetails(orderItems, savedOrder);

        savePayment(paymentDTO, savedOrder);
        pendingOrderService.deletePendingOrder(findPendingOrder.getOrderId());

        List<OrderDetailDTO> orderDetails = orderDetailQueryService.findOrderDetailByOrderId(paymentDTO.orderId());

        return PaymentConfirmResponse.builder()
                .orderDetails(orderDetails)
                .build();
    }

    private List<PendingOrderBookItemsRequest> validateBookStocks(PendingOrder findPendingOrder) {
        List<PendingOrderBookItemsRequest> orderDataList = findPendingOrder.getOrderData();

        orderDataList.forEach(orderData -> {
            Book findBook = bookRepository.findBookByBookId(orderData.bookId())
                    .orElseThrow(() -> new BookInfoNotFoundException("도서 정보를 찾을 수 없습니다."));

            int stockQuantity = findBook.getStockQuantity() - orderData.quantity();

            if (stockQuantity < 0) {
                log.info("stockQuantity = {}", stockQuantity);
                throw new InsufficientBookStockException("도서 재고 수량이 부족하여 결제를 진행할 수 없습니다.");
            }

            findBook.updateStockQuantity(stockQuantity, LocalDateTime.now().withNano(0));
        });
        return orderDataList;
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