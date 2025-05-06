package com.jamjam.bookjeok.domains.member.command.repository;

import com.jamjam.bookjeok.domains.member.command.entity.InterestAuthor;
import com.jamjam.bookjeok.domains.member.command.entity.InterestAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestAuthorRepository extends JpaRepository<InterestAuthor, InterestAuthorId> {

    @Query("SELECT COUNT(ia) FROM InterestAuthor ia WHERE ia.interestAuthorId.memberUid = :memberUid")
    int countInterestAuthorsByMemberUid(@Param("memberUid") Long memberUid);

}
