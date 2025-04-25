package com.jamjam.bookjeok.domains.payment.command.service;

import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.entity.Payment;
import com.jamjam.bookjeok.domains.payment.command.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

/**
 * 결제 엔티티 처리를 담당하는 서비스 클래스입니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentEntityCommandServiceImpl implements PaymentEntityCommandService {

    private final PaymentRepository paymentRepository;

    /**
     * 결제 DTO와 주문 정보를 기반으로 결제 엔티티를 생성하고 저장합니다.
     *
     * @param paymentDTO 결제 정보가 담긴 DTO
     * @param order 결제와 연관된 주문 객체
     * @return 저장된 결제 엔티티
     */
    @Override
    public void createPayment(PaymentDTO paymentDTO, Order order) {
        Payment payment = createPaymentEntity(paymentDTO, order);
        paymentRepository.save(payment);
    }

    /**
     * 결제 DTO와 주문 정보를 기반으로 결제 엔티티 객체를 생성합니다.
     *
     * @param paymentDTO 결제 정보가 담긴 DTO
     * @param order 결제와 연관된 주문 객체
     * @return 생성된 결제 엔티티 객체
     */
    private Payment createPaymentEntity(PaymentDTO paymentDTO, Order order) {
        return Payment.builder()
                .orderUid(order.getOrderUid())
                .paymentKey(paymentDTO.paymentKey())
                .paymentType(paymentDTO.type())
                .paymentMethod(paymentDTO.method())
                .totalAmount(paymentDTO.totalAmount())
                .requestedAt(OffsetDateTime.parse(paymentDTO.requestedAt()).toLocalDateTime())
                .approvedAt(OffsetDateTime.parse(paymentDTO.approvedAt()).toLocalDateTime())
                .build();
    }

}