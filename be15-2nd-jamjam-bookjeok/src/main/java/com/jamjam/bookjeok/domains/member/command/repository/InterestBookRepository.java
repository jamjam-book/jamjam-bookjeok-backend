package com.jamjam.bookjeok.domains.member.command.repository;

import com.jamjam.bookjeok.domains.member.command.entity.InterestBook;
import com.jamjam.bookjeok.domains.member.command.entity.InterestBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestBookRepository extends JpaRepository<InterestBook, InterestBookId> {

    boolean existsById(InterestBookId interestBookId);

}