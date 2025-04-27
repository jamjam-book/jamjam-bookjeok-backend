package com.jamjam.bookjeok.domains.member.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String resetToken;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Builder
    public PasswordResetToken(String memberId, String resetToken, LocalDateTime expiryTime) {
        this.memberId = memberId;
        this.resetToken = resetToken;
        this.expiryTime = expiryTime;
    }

}