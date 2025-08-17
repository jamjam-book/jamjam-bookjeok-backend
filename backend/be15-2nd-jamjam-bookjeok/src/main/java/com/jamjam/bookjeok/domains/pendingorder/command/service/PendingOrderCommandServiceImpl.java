package com.jamjam.bookjeok.domains.pendingorder.command.service;

import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.entity.PendingOrder;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderRequest;
import com.jamjam.bookjeok.domains.pendingorder.command.dto.response.PendingOrderResponse;
import com.jamjam.bookjeok.domains.pendingorder.command.repository.PendingOrderRepository;
import com.jamjam.bookjeok.domains.cart.query.mapper.CartMapper;
import com.jamjam.bookjeok.exception.cart.CartItemLimitExceededException;
import com.jamjam.bookjeok.exception.payment.PaymentOrderNotFountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PendingOrderCommandServiceImpl implements PendingOrderCommandService {

    private final CartMapper cartMapper;
    private final PendingOrderRepository pendingOrderRepository;

    @Override
    public PendingOrderResponse createOrder(PendingOrderRequest pendingOrderRequest) {
        List<Book> validatedBooks = validateOrderBooks(pendingOrderRequest);
        int totalAmount = calculateTotalAmount(pendingOrderRequest, validatedBooks);
        PendingOrder pendingOrder = createPendingOrder(pendingOrderRequest, totalAmount);
        PendingOrder savedPendingOrder = pendingOrderRepository.save(pendingOrder);

        return toResponse(savedPendingOrder);
    }

    @Override
    public PendingOrder getPendingOrder(PaymentConfirmRequest paymentConfirmRequest) {
        return pendingOrderRepository.findPendingOrderByOrderIdAndTotalAmount(paymentConfirmRequest.orderId(), paymentConfirmRequest.amount())
                .orElseThrow(() -> new PaymentOrderNotFountException("주문 정보가 일치하지 않습니다."));
    }

    @Override
    public void deletePendingOrder(String orderId) {
        pendingOrderRepository.deletePendingOrderByOrderId(orderId);
    }

    private List<Book> validateOrderBooks(PendingOrderRequest pendingOrderRequest) {
        return pendingOrderRequest.orderBookItems().stream()
                .map(orderBookInfo ->
                        cartMapper.findByBookIdAndBookName(
                                orderBookInfo.bookId(),
                                orderBookInfo.bookName()
                        ).orElseThrow(() -> new CartItemLimitExceededException("존재하지 않는 도서 정보 입니다."))
                ).toList();
    }

    private int calculateTotalAmount(PendingOrderRequest pendingOrderRequest, List<Book> validatedBooks) {
        Map<Long, Integer> quantityMap = pendingOrderRequest.orderBookItems().stream()
                .collect(Collectors.toMap(
                        PendingOrderBookItemsRequest::bookId, PendingOrderBookItemsRequest::quantity
                ));

        return validatedBooks.stream()
                .mapToInt(book -> book.getPrice() * quantityMap.get(book.getBookId()))
                .sum();
    }

    private PendingOrder createPendingOrder(PendingOrderRequest pendingOrderRequest, int totalAmount) {
        return PendingOrder.builder()
                .memberUid(pendingOrderRequest.memberUid())
                .totalAmount(totalAmount)
                .orderData(pendingOrderRequest.orderBookItems())
                .build();
    }

    private PendingOrderResponse toResponse(PendingOrder savedPendingOrder) {
        return PendingOrderResponse.builder()
                .orderId(savedPendingOrder.getOrderId())
                .totalAmount(savedPendingOrder.getTotalAmount())
                .build();
    }

}