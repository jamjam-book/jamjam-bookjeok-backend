package com.jamjam.bookjeok.domains.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "follows")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @Column(name = "following_uid")
    private Long followingUid;

    @Column(name = "follower_uid")
    private Long followerUid;

    @Builder
    public Follow(Long followingUid, Long followerUid) {
        this.followingUid = followingUid;
        this.followerUid = followerUid;
    }

}