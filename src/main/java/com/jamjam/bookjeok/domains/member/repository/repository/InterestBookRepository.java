package com.jamjam.bookjeok.domains.member.repository.repository;

import com.jamjam.bookjeok.domains.member.entity.InterestBook;
import com.jamjam.bookjeok.domains.member.entity.InterestBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestBookRepository extends JpaRepository<InterestBook, InterestBookId> {

    boolean existsById(InterestBookId interestBookId);

}