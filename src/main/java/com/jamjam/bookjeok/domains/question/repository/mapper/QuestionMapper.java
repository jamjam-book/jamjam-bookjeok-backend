package com.jamjam.bookjeok.domains.question.repository.mapper;

import com.jamjam.bookjeok.domains.question.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.dto.QuestionListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper{
    List<QuestionListDTO> findQuestions(Map<String, Object> params);

    QuestionDTO findQuestionByQuestionId(Map<String, Object> params);
}
