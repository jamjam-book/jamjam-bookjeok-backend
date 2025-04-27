package com.jamjam.bookjeok.domains.member.command.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}