package com.jamjam.bookjeok.domains.order.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_uid")
    private Long orderUid;

    @Column(name = "member_uid")
    private Long memberUid;

    @Column(name = "order_status_id")
    private Integer orderStatusId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "total_amount")
    private int totalAmount;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Builder
    public Order(
            Long memberUid, Integer orderStatusId, String orderId,
            String orderName, int totalAmount, LocalDateTime orderedAt, LocalDateTime canceledAt,
            LocalDateTime refundedAt, LocalDateTime createdAt, LocalDateTime modifiedAt
    ) {
        this.memberUid = memberUid;
        this.orderStatusId = orderStatusId;
        this.orderId = orderId;
        this.orderName = orderName;
        this.totalAmount = totalAmount;
        this.orderedAt = orderedAt;
        this.canceledAt = canceledAt;
        this.refundedAt = refundedAt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}