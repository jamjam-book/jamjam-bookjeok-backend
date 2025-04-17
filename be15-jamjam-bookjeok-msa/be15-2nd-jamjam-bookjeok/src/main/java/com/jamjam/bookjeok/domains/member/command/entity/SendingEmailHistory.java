package com.jamjam.bookjeok.domains.member.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "sending_email_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendingEmailHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sending_email_id")
    private Long sendingEmailId;

    @Column(name = "member_uid")
    private Long memberUid;

    @Lob
    @Column(name = "email_content")
    private String emailContent;

    @Column(name = "sending_status_value")
    private Boolean sendingStatusValue;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Builder
    public SendingEmailHistory(
            Long memberUid, String emailContent,
            Boolean sendingStatusValue, LocalDateTime sentAt
    ) {
        this.memberUid = memberUid;
        this.emailContent = emailContent;
        this.sendingStatusValue = sendingStatusValue;
        this.sentAt = sentAt;
    }

}