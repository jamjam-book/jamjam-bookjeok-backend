package com.jamjam.bookjeok.domains.pendingorder.command.entity;

import com.jamjam.bookjeok.domains.pendingorder.command.dto.request.PendingOrderBookItemsRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "pending_orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PendingOrder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pending_order_id")
    private Long pendingOrderId;

    @Column(name = "member_uid")
    private Long memberUid;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "total_amount")
    private int totalAmount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "order_data", columnDefinition = "json")
    private List<PendingOrderBookItemsRequest> orderData;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public PendingOrder(
            Long memberUid, int totalAmount,
            List<PendingOrderBookItemsRequest> orderData, LocalDateTime createdAt) {
        this.memberUid = memberUid;
        this.orderId = createOrderId();
        this.totalAmount = totalAmount;
        this.orderData = orderData;
        this.createdAt = createdAt;
    }

    public String createOrderId() {
        final int UUID_START_INDEX = 0;
        final int UUID_END_INDEX = 8;
        final String DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        final String ORDER_UUID = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(UUID_START_INDEX, UUID_END_INDEX);
        return DATE + ORDER_UUID;
    }

}