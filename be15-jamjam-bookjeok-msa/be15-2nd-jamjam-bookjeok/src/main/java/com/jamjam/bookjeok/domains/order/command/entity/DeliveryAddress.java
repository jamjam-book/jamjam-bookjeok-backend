package com.jamjam.bookjeok.domains.order.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "delivery_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryAddress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_id")
    private Long deliveryAddressId;

    @Column(name = "member_uid")
    private Long memberUid;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "recipient_phone")
    private String recipientPhone;

    @Column(name = "delivery_address_category")
    private String deliveryAddressCategory;

    @Column(name = "address")
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "request_note")
    private String requestNote;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Builder
    public DeliveryAddress(
            Long memberUid, String recipientName, String recipientPhone,
            String deliveryAddressCategory, String address, String addressDetail,
            String postalCode, String requestNote, LocalDateTime createdAt,
            LocalDateTime modifiedAt, boolean isDeleted
    ) {
        this.memberUid = memberUid;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.deliveryAddressCategory = deliveryAddressCategory;
        this.address = address;
        this.addressDetail = addressDetail;
        this.postalCode = postalCode;
        this.requestNote = requestNote;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

}