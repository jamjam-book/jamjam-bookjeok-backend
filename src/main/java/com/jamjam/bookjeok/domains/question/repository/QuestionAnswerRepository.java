package com.jamjam.bookjeok.domains.question.repository;

import com.jamjam.bookjeok.domains.question.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
    Optional<QuestionAnswer> findByAnswerId(Long answerId);
}
