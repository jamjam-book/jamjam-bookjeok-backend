package com.jamjam.bookjeok.domains.order.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "order_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderStatus {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Integer orderStatusId;

    @Column(name = "order_status_name")
    private String orderStatusName;

    @Builder
    public OrderStatus(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

}