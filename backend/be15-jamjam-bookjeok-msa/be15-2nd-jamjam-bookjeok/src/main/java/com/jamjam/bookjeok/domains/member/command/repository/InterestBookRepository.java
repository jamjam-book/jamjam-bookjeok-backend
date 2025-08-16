package com.jamjam.bookjeok.domains.member.command.repository;

import com.jamjam.bookjeok.domains.member.command.entity.InterestBook;
import com.jamjam.bookjeok.domains.member.command.entity.InterestBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestBookRepository extends JpaRepository<InterestBook, InterestBookId> {

    @Query("SELECT COUNT(ib) FROM InterestBook ib WHERE ib.interestBookId.memberUid = :memberUid")
    int countInterestBookByMemberUid(@Param("memberUid") Long memberUid);

    boolean existsById(InterestBookId interestBookId);

}