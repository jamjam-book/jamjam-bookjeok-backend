package com.jamjam.bookjeok.domains.payment.query.service;

import com.jamjam.bookjeok.domains.order.query.mapper.OrderMapper;
import com.jamjam.bookjeok.domains.payment.query.dto.PaymentDetailDTO;
import com.jamjam.bookjeok.domains.payment.command.entity.Payment;
import com.jamjam.bookjeok.domains.payment.query.mapper.PaymentDetailMapper;
import com.jamjam.bookjeok.domains.payment.query.mapper.PaymentMapper;
import com.jamjam.bookjeok.exception.payment.PaymentNotFountException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentDetailServiceImpl implements PaymentDetailService {

    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final PaymentDetailMapper paymentDetailMapper;

    @Override
    public PaymentDetailDTO getPaymentDetail(Long paymentId) {
        Payment findPayment = paymentMapper.findPaymentByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentNotFountException("결제 정보를 찾을 수 없습니다."));

        String paymentMethod = findPayment.getPaymentMethod();

        return paymentDetailMapper.findPaymentDetailByPaymentId(paymentId, paymentMethod);
    }

    @Override
    public Long getPaymentId(String orderId) {
        Long orderUid = orderMapper.findOrderUidByOrderId(orderId);
        return orderMapper.findPaymentIdByOrderUid(orderUid);
    }

}