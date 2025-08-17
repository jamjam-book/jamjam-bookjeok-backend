package com.jamjam.bookjeok.domains.question.query.service;

import com.jamjam.bookjeok.domains.question.command.dto.response.QuestionAnswerResponse;
import com.jamjam.bookjeok.domains.question.command.entity.QuestionAnswer;
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
public class QuestionAdminQueryServiceImpl implements QuestionAdminQueryService {

    private final QuestionMapper questionMapper;

    @Override
    public List<QuestionListDTO> findQuestions(Map<String, Object> params) {

        return questionMapper.findQuestions(params);

    }

    @Override
    public QuestionDTO findQuestionByQuestionId(Map<String, Object> params) {

        return questionMapper.findQuestionByQuestionId(params);

    }

    private QuestionAnswerResponse buildQuestionAnswerResponse(QuestionAnswer answer) {

        return QuestionAnswerResponse.builder()
                .answerId(answer.getAnswerId())
                .questionId(answer.getQuestionId())
                .writerUid(answer.getWriterUid())
                .contents(answer.getContents())
                .modifiedAt(answer.getModifiedAt())
                .isDeleted(answer.isDeleted())
                .build();

    }

}
