package com.jamjam.bookjeok.domains.cart.command.entity;

import com.jamjam.bookjeok.exception.cart.CartItemLimitExceededException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "carts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "member_uid")
    private Long memberUid;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Builder
    public Cart(
            Long memberUid, Long bookId, int quantity,
            LocalDateTime createdAt, LocalDateTime modifiedAt
    ) {
        this.memberUid = memberUid;
        this.bookId = bookId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static void validateCartItemLimit(final int cartItemCountFromDb) {
        final int MAX_CART_COUNT = 20;

        if (cartItemCountFromDb >= MAX_CART_COUNT) {
            throw new CartItemLimitExceededException("장바구니에는 최대 20까지 도서를 담을 수 있습니다.");
        }
    }

    public static int calculateBookTotalPrice(final int quantity, final int price) {
        return quantity * price;
    }

    public void updateQuantity(final int quantity) {
        this.quantity = quantity;
    }

}