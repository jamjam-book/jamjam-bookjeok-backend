package com.jamjam.bookjeok.domains.member.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jamjam.bookjeok.domains.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<com.jamjam.bookjeok.domains.member.entity.Member, Long>{

    Optional<Member> findByMemberId(String memberId);

}
