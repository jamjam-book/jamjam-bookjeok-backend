package com.jamjam.bookjeok.domains.member.command.service;

public interface FollowCommandService {
    String createFollow(String followingId, String followerId);

    void deleteFollow(String followingId, String followerId);
}
