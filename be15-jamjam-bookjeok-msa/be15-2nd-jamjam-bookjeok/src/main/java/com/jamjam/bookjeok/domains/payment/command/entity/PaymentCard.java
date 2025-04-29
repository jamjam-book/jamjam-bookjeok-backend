package com.jamjam.bookjeok.domains.payment.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payment_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCard {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_card_id")
    private Long paymentCardId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "issuer_code")
    private String issuerCode;

    @Column(name = "acquirer_code")
    private String acquirerCode;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "installment_plan_months")
    private int installmentPlanMonths;

    @Column(name = "approve_no")
    private String approveNo;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "owner_type")
    private String ownerType;

    @Column(name = "acquire_status")
    private String acquireStatus;

    @Column(name = "is_interest_free")
    private boolean isInterestFree = false;

    @Builder
    public PaymentCard(
            Long paymentId, int amount, String issuerCode, String acquirerCode,
            String cardNumber, int installmentPlanMonths, String approveNo,
            String cardType, String ownerType, String acquireStatus, boolean isInterestFree
    ) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.issuerCode = issuerCode;
        this.acquirerCode = acquirerCode;
        this.cardNumber = cardNumber;
        this.installmentPlanMonths = installmentPlanMonths;
        this.approveNo = approveNo;
        this.cardType = cardType;
        this.ownerType = ownerType;
        this.acquireStatus = acquireStatus;
        this.isInterestFree = isInterestFree;
    }

}