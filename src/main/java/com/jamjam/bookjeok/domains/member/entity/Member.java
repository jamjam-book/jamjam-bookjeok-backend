package com.jamjam.bookjeok.domains.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_uid")
    private Long memberUid;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "password")
    private String password;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "marketing_consent")
    private boolean marketingConsent = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private MemberRole role = MemberRole.MEMBER;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_status")
    private MemberActivityStatus activityStatus;

    @Builder
    public Member(
            String memberId, String password, String memberName, String phoneNumber,
            String email, String nickname, String birthDate, boolean marketingConsent,
            MemberRole role, LocalDateTime createdAt, LocalDateTime modifiedAt, MemberActivityStatus activityStatus
    ) {
        this.memberId = memberId;
        this.password = password;
        this.memberName = memberName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.marketingConsent = marketingConsent;
        this.role = role;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.activityStatus = activityStatus;
    }

}