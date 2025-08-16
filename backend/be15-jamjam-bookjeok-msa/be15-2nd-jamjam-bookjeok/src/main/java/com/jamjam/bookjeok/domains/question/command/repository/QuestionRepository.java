package com.jamjam.bookjeok.domains.question.command.repository;

import com.jamjam.bookjeok.domains.question.command.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByQuestionId(Long questionId);
}
