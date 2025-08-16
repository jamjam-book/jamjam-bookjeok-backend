package com.jamjam.bookjeok.domains.payment.command.service;

import com.jamjam.bookjeok.domains.order.command.entity.Order;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.entity.Payment;
import com.jamjam.bookjeok.domains.payment.command.entity.PaymentCard;
import com.jamjam.bookjeok.domains.payment.command.entity.PaymentEasyPay;
import com.jamjam.bookjeok.domains.payment.command.repository.PaymentCardRepository;
import com.jamjam.bookjeok.domains.payment.command.repository.PaymentEasyPayRepository;
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
    private final PaymentCardRepository paymentCardRepository;
    private final PaymentEasyPayRepository paymentEasyPayRepository;

    /**
     * 결제 DTO와 주문 정보를 기반으로 결제 엔티티를 생성하고 저장합니다.
     * 결제 수단에 따라 카드 결제 또는 간편결제 정보도 추가로 저장합니다.
     *
     * @param paymentDTO 결제 정보가 담긴 DTO
     * @param order 결제와 연관된 주문 객체
     */
    @Override
    public void createPayment(PaymentDTO paymentDTO, Order order) {
        Payment payment = createPaymentEntity(paymentDTO, order);
        Payment savedPayment = paymentRepository.save(payment);

        switch (paymentDTO.method()) {
            case "카드" -> {
                PaymentCard paymentCard = createPaymentCard(paymentDTO, payment);
                paymentCardRepository.save(paymentCard);
            }
            case "간편결제" -> {
                PaymentEasyPay paymentEasyPay = createPaymentEasyPay(paymentDTO, payment);
                paymentEasyPayRepository.save(paymentEasyPay);
            }
        }
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

    /**
     * 카드 결제 정보를 기반으로 PaymentCard 엔티티를 생성합니다.
     *
     * @param paymentDTO 결제 정보가 담긴 DTO
     * @param payment    결제 엔티티
     * @return 생성된 PaymentCard 엔티티 객체
     */
    private PaymentCard createPaymentCard(PaymentDTO paymentDTO, Payment payment) {
        return PaymentCard.builder()
                .paymentId(payment.getPaymentId())
                .amount(paymentDTO.card().amount())
                .issuerCode(paymentDTO.card().issuerCode())
                .acquirerCode(paymentDTO.card().acquirerCode())
                .cardNumber(paymentDTO.card().number())
                .installmentPlanMonths(paymentDTO.card().installmentPlanMonths())
                .approveNo(paymentDTO.card().approveNo())
                .cardType(paymentDTO.card().cardType())
                .ownerType(paymentDTO.card().ownerType())
                .acquireStatus(paymentDTO.card().acquireStatus())
                .isInterestFree(paymentDTO.card().isInterestFree())
                .build();
    }

    /**
     * 간편결제 정보를 기반으로 PaymentEasyPay 엔티티를 생성합니다.
     *
     * @param paymentDTO 결제 정보가 담긴 DTO
     * @param payment    결제 엔티티
     * @return 생성된 PaymentEasyPay 엔티티 객체
     */
    private PaymentEasyPay createPaymentEasyPay(PaymentDTO paymentDTO, Payment payment) {
        return PaymentEasyPay.builder()
                .paymentId(payment.getPaymentId())
                .provider(paymentDTO.easyPay().provider())
                .amount(paymentDTO.easyPay().amount())
                .discountAmount(paymentDTO.easyPay().discountAmount())
                .build();
    }

}