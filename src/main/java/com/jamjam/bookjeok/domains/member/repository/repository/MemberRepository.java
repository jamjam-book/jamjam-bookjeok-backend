package com.jamjam.bookjeok.domains.member.repository.repository;

import com.jamjam.bookjeok.domains.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{

    Optional<Member> findByMemberId(String memberId);

}
