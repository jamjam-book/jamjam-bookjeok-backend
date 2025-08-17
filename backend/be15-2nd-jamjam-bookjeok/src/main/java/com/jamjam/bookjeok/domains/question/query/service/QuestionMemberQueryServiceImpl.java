package com.jamjam.bookjeok.domains.question.query.service;

import com.jamjam.bookjeok.domains.question.command.dto.response.QuestionResponse;
import com.jamjam.bookjeok.domains.question.command.entity.Question;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionCategoryDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.query.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.query.mapper.QuestionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionMemberQueryServiceImpl implements QuestionMemberQueryService {

    private final QuestionMapper questionMapper;

    @Override
    public List<QuestionListDTO> findMemberQuestions(Map<String, Object> params) {
        return questionMapper.findMemberQuestions(params);
    }

    @Override
    public QuestionDTO findMemberQuestion(Map<String, Object> params) {
        return questionMapper.findQuestionByQuestionId(params);
    }

    @Override
    public List<QuestionCategoryDTO> getQuestionCategories() {
        return questionMapper.findQuestionCategories();
    }

    private QuestionResponse buildQuestionResponse(Question question) {

        return QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .questionCategoryId(question.getQuestionCategoriesId())
                .writerUid(question.getWriterUid())
                .title(question.getTitle())
                .contents(question.getContents())
                .createdAt(question.getCreatedAt())
                .modifiedAt(question.getModifiedAt())
                .isDeleted(question.isDeleted())
                .build();
    }
}
