package com.jamjam.bookjeok.domains.member.repository.repository;

import com.jamjam.bookjeok.domains.member.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {

    boolean existsByFollowingUidAndFollowerUid(Long followingUid, Long followerUid);

}
