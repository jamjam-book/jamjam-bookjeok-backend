package com.jamjam.bookjeok.domains.question.command.repository;

import com.jamjam.bookjeok.domains.question.command.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
    Optional<QuestionAnswer> findByAnswerId(Long answerId);

    Optional<List<QuestionAnswer>> findByQuestionId(Long questionId);
}
