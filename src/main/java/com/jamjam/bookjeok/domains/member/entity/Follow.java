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

    @Column(name = "following_id")
    private Long followingId;

    @Column(name = "follower_id")
    private Long followerId;

    @Builder
    public Follow(Long followingId, Long followerId) {
        this.followingId = followingId;
        this.followerId = followerId;
    }

}