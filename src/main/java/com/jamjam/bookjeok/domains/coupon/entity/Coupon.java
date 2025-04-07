package com.jamjam.bookjeok.domains.coupon.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "coupons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_detail")
    private String couponDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discount_type;

    @Column(name = "discount_value")
    private int discount_value;

    @Column(name = "min_order_amount")
    private int min_order_amount = 0;

    @Column(name = "max_discount_amount")
    private int max_discount_amount;

    @Column(name = "coupon_started_at")
    private LocalDateTime couponStartedAt;

    @Column(name = "coupon_expired_at")
    private LocalDateTime couponExpiredAt;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    @Builder
    public Coupon(String couponName, String couponDetail,
                  DiscountType discount_type, int discount_value,
                  int min_order_amount, int max_discount_amount,
                  LocalDateTime couponStartedAt, LocalDateTime couponExpiredAt, boolean deleted
    ) {
        this.couponName = couponName;
        this.couponDetail = couponDetail;
        this.discount_type = discount_type;
        this.discount_value = discount_value;
        this.min_order_amount = min_order_amount;
        this.max_discount_amount = max_discount_amount;
        this.couponStartedAt = couponStartedAt;
        this.couponExpiredAt = couponExpiredAt;
        this.deleted = deleted;
    }
}