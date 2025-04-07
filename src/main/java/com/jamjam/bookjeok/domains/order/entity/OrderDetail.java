package com.jamjam.bookjeok.domains.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "order_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "order_uid")
    private Long orderUid;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "is_canceled")
    private boolean isCanceled = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public OrderDetail(
            Long orderUid, Long bookId, int price,
            int quantity, boolean isCanceled, LocalDateTime createdAt
    ) {
        this.orderUid = orderUid;
        this.bookId = bookId;
        this.price = price;
        this.quantity = quantity;
        this.isCanceled = isCanceled;
        this.createdAt = createdAt;
    }

}