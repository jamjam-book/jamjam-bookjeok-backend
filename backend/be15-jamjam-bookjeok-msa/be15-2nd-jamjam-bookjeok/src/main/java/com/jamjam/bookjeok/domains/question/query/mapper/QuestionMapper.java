package com.jamjam.bookjeok.domains.question.query.mapper;

import com.jamjam.bookjeok.domains.question.query.dto.QuestionCategoryDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper {
    List<QuestionListDTO> findQuestions(Map<String, Object> params);

    QuestionDTO findQuestionByQuestionId(Map<String, Object> params);

    List<QuestionListDTO> findMemberQuestions(Map<String, Object> params);

    List<QuestionCategoryDTO> findQuestionCategories();
}
