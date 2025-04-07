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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uid")
    private Member member;

    @Builder
    public StockNotification(Book book, Member member) {
        this.book = book;
        this.member = member;
    }

}