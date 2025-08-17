package com.jamjam.bookjeok.domains.orderdetail.query.service;

import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailBookDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.OrderDetailDTO;
import com.jamjam.bookjeok.domains.orderdetail.query.dto.response.OrderDetailResponse;
import com.jamjam.bookjeok.domains.orderdetail.query.mapper.OrderDetailMapper;
import com.jamjam.bookjeok.domains.payment.query.dto.PaymentDetailDTO;
import com.jamjam.bookjeok.domains.payment.query.service.PaymentDetailService;
import com.jamjam.bookjeok.exception.orderdetail.OrderDetailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailQueryServiceImpl implements OrderDetailQueryService {

    private final PaymentDetailService paymentDetailService;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDetailResponse getOrderDetailByMemberUidAndOrderId(Long memberUid, String orderId) {
        List<OrderDetailDTO> orderDetails = orderDetailMapper.findOrderDetailByMemberUidAndOrderId(memberUid, orderId);

        if (orderDetails == null) {
            throw new OrderDetailNotFoundException("요청하신 주문 내역은 존재하지 않습니다.");
        }

        Long paymentId = paymentDetailService.getPaymentId(orderId);
        PaymentDetailDTO paymentDetail = paymentDetailService.getPaymentDetail(paymentId);

        List<OrderDetailBookDTO> books = orderDetails.stream()
                .map(o -> OrderDetailBookDTO.builder()
                        .bookId(o.bookId())
                        .bookName(o.bookName())
                        .isbn(o.isbn())
                        .quantity(o.quantity())
                        .totalPrice(o.totalPrice())
                        .imageUrl(o.imageUrl())
                        .build())
                .toList();


        return OrderDetailResponse.builder()
                .orderId(orderId)
                .orderedAt(orderDetails.get(0).orderedAt())
                .books(books)
                .paymentDetail(paymentDetail)
                .build();
    }

}