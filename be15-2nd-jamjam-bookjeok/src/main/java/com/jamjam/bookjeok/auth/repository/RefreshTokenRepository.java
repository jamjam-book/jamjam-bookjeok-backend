package com.jamjam.bookjeok.auth.repository;

import com.jamjam.bookjeok.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findRefreshTokenByMemberId(String memberId);

}