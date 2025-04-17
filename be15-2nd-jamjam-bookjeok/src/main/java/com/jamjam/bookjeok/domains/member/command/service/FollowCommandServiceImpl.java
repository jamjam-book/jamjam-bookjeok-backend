package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.member.command.entity.Follow;
import com.jamjam.bookjeok.domains.member.entity.Member;
import com.jamjam.bookjeok.domains.member.command.repository.FollowRepository;
import com.jamjam.bookjeok.domains.member.command.repository.MemberRepository;
import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import com.jamjam.bookjeok.exception.member.MemberException;
import com.jamjam.bookjeok.exception.member.followException.AlreadyFollowException;
import com.jamjam.bookjeok.exception.member.followException.NotFollowException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowCommandServiceImpl implements FollowCommandService{

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public String createFollow(String followingId, String followerId){

        // followerId를 통해 팔로워 하는 사람의 Uid 가져오기 (나중에 변경할 예정)
        Member follower = memberRepository.findByMemberId(followerId)
                .orElseThrow(() -> {
                    log.error("팔로워 ID {} 찾을 수 없음", followerId);
                    return new MemberException(MemberErrorCode.NOT_EXIST_MEMBER);
                });

        // 팔로잉 하고자 하는 사람
        Member following = memberRepository.findByMemberId(followingId)
                .orElseThrow(() -> {
                    log.error("팔로워 ID {} 찾을 수 없음", followingId);
                    return new MemberException(MemberErrorCode.NOT_EXIST_MEMBER);
                });

        // 각각의 사람들의 uid 가져오기
        Long followerUid = follower.getMemberUid();
        Long followingUid = following.getMemberUid();

        log.info("팔로우 관계 확인: followerUid = {}, followingUid = {}", followerUid, followingUid);
        boolean isAlreadyFollowing = isFollowing(followingUid, followerUid);
        log.info("팔로우 관계 존재 여부: {}", isAlreadyFollowing);

        // 팔로우를 하고 있는 사람이라면 예외 처리
        if(isFollowing(followingUid, followerUid)) {
            throw new AlreadyFollowException(MemberErrorCode.ALREADY_FOLLOW);
        }

        Follow follow = Follow.builder()
                .followingUid(followingUid)
                .followerUid(followerUid)
                .build();
        followRepository.save(follow);

        return following.getNickname();
    }

    @Override
    @Transactional
    public void deleteFollow(String followingId, String followerId){
        // followerId를 통해 팔로워 하는 사람의 Uid 가져오기 (나중에 변경할 예정)
        Member follower = memberRepository.findByMemberId(followerId)
                .orElseThrow(() -> {
                    log.error("팔로워 ID {} 찾을 수 없음", followerId);
                    return new MemberException(MemberErrorCode.NOT_EXIST_MEMBER);
                });

        // 팔로잉 하고자 하는 사람
        Member following = memberRepository.findByMemberId(followingId)
                .orElseThrow(() -> {
                    log.error("팔로워 ID {} 찾을 수 없음", followingId);
                    return new MemberException(MemberErrorCode.NOT_EXIST_MEMBER);
                });

        Long followingUid =  following.getMemberUid();
        Long followerUid = follower.getMemberUid();

        log.info("팔로우 하고 있나요? {}", isFollowing(followingUid, followerUid));

        if(!isFollowing(followingUid, followerUid)){
            throw new NotFollowException(MemberErrorCode.NOT_FOLLOW);
        }

        Follow follow = followRepository.findByFollowingUidAndFollowerUid(followingUid, followerUid)
                .orElseThrow(() -> new NotFollowException(MemberErrorCode.NOT_FOLLOW));

        followRepository.delete(follow);
    }

    private boolean isFollowing(Long followingUid, Long followerUid){
        return followRepository.existsByFollowingUidAndFollowerUid(followingUid, followerUid);
    }
}