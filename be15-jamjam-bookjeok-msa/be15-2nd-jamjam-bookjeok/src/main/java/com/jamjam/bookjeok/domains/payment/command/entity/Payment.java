package com.jamjam.bookjeok.domains.payment.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "order_uid")
    private Long orderUid;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total_amount")
    private int totalAmount;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "cancel_amount")
    private int cancelAmount;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Builder
    public Payment(
            Long orderUid, String paymentKey, String paymentType,
            String paymentMethod, int totalAmount, LocalDateTime requestedAt,
            LocalDateTime approvedAt, int cancelAmount, LocalDateTime canceledAt
    ) {
        this.orderUid = orderUid;
        this.paymentKey = paymentKey;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.cancelAmount = cancelAmount;
        this.canceledAt = canceledAt;
    }

}