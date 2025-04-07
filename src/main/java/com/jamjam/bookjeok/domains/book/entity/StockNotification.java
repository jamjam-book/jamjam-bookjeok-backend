package com.jamjam.bookjeok.domains.book.entity;

import com.jamjam.bookjeok.domains.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stock_notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockNotification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "member_uid")
    private Long memberUid;

    @Builder
    public StockNotification(Long bookId, Long memberUid) {
        this.bookId = bookId;
        this.memberUid = memberUid;
    }

}