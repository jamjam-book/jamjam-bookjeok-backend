package com.jamjam.bookjeok.domains.point.entity;

import com.jamjam.bookjeok.domains.member.entity.Member;
import com.jamjam.bookjeok.domains.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "points")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_save_id")
    private Long pointSaveId;

    @Column(name = "member_uid")
    private Long memberUid;

    @Column(name = "order_uid")
    private Long orderUid;

    @Column(name = "point_type")
    private PointType pointType;

    @Column(name = "save_amount")
    private int saveAmount;

    @Column(name = "saved_at")
    private LocalDateTime savedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted")
    private boolean deleted = false;

    @Builder
    public Point(
            Long memberUid, Long orderUid, PointType pointType,
            int saveAmount, LocalDateTime savedAt, LocalDateTime createdAt,
            LocalDateTime modifiedAt, boolean deleted
    ) {
        this.memberUid = memberUid;
        this.orderUid = orderUid;
        this.pointType = pointType;
        this.saveAmount = saveAmount;
        this.savedAt = savedAt;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deleted = deleted;
    }
}