package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.domains.member.dto.FollowDTO;
import com.jamjam.bookjeok.domains.member.dto.PostSummaryDTO;
import com.jamjam.bookjeok.domains.member.dto.request.InterestAuthorRequest;
import com.jamjam.bookjeok.domains.member.entity.Follow;
import com.jamjam.bookjeok.domains.member.entity.Member;
import com.jamjam.bookjeok.domains.member.repository.mapper.FollowMapper;
import com.jamjam.bookjeok.domains.member.repository.repository.FollowRepository;
import com.jamjam.bookjeok.domains.member.repository.repository.MemberRepository;
import com.jamjam.bookjeok.exception.coupon.ErrorCode;
import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import com.jamjam.bookjeok.exception.member.MemberException;
import com.jamjam.bookjeok.exception.member.followException.AlreadyFollowException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {

    private final FollowMapper followMapper;
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<FollowDTO> getFollowingListByMemberId(String memberId) {
        return followMapper.findFollowingListByMemberId(memberId);
    }

    @Transactional(readOnly = true)
    public List<PostSummaryDTO> getPostListByWriterId(String writerId) {
        return followMapper.findPostListByMemberId(writerId);
    }

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

        Follow follow = new Follow(followingUid, followerUid);

        followRepository.save(follow);

        return following.getNickname();
    }

    private boolean isFollowing(Long followingUid, Long followerUid){
        return followRepository.existsByFollowingUidAndFollowerUid(followingUid, followerUid);
    }
}