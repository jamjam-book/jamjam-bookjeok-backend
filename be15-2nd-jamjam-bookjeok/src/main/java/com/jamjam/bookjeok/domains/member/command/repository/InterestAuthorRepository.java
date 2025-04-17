package com.jamjam.bookjeok.domains.member.command.repository;

import com.jamjam.bookjeok.domains.member.command.entity.InterestAuthor;
import com.jamjam.bookjeok.domains.member.command.entity.InterestAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestAuthorRepository extends JpaRepository<InterestAuthor, InterestAuthorId> {
}
