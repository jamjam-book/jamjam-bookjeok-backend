package com.jamjam.bookjeok.domains.member.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberUid;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "marketing_consent")
    @ColumnDefault("1")
    private Boolean marketingConstant;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @ColumnDefault("'MEMBER'")
    private MemberRole role;

    @Column(name = "created_at")

    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;

    @Column(name = "activity_status")
    private String activityStatus;

}
