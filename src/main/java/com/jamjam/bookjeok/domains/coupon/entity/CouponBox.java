package com.jamjam.bookjeok.domains.coupon.entity;

import com.jamjam.bookjeok.domains.member.entity.Member;
import com.jamjam.bookjeok.domains.order.entity.Order;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member_coupon_boxs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponBox {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long memberCouponId;

    @Column(name = "member_uid")
    private Long memberUid;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "order_uid")
    private Long orderUid;

    @Column(name = "is_used")
    private boolean isUsed = false;

    public CouponBox(
            Long memberUid, Long couponId,
            Long orderUid, boolean isUsed
    ) {
        this.memberUid = memberUid;
        this.couponId = couponId;
        this.orderUid = orderUid;
        this.isUsed = isUsed;
    }

}