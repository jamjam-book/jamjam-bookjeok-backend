package com.jamjam.bookjeok.domains.payment.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment_easypay")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEasyPay {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_easypay_id")
    private Long paymentEasyPayId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "amount")
    private int amount;

    @Column(name = "discount_amount")
    private int discountAmount;

    @Builder

    public PaymentEasyPay(
            Long paymentId, String provider, int amount, int discountAmount
    ) {
        this.paymentId = paymentId;
        this.provider = provider;
        this.amount = amount;
        this.discountAmount = discountAmount;
    }

}