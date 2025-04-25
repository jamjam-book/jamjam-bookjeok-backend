package com.jamjam.bookjeok.domains.payment.command.infrastructure.service;

import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.TossPaymentApproveRequest;
import com.jamjam.bookjeok.domains.payment.command.infrastructure.client.TossPaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Toss 결제 요청을 처리하는 서비스 클래스입니다.
 * 실제 API 통신은 TossPaymentClient에 위임합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TossPaymentCommandServiceImpl implements TossPaymentCommandService {

    private final TossPaymentClient tossPaymentClient;

    /**
     * Toss Payments API를 통해 결제를 승인합니다.
     *
     * @param tossPaymentApproveRequest 결제 승인 요청 정보
     * @return Toss로부터 받은 결제 응답 정보
     */
    @Override
    public PaymentDTO approvePayment(TossPaymentApproveRequest tossPaymentApproveRequest) {
        return tossPaymentClient.requestPaymentApproval(tossPaymentApproveRequest);
    }

}