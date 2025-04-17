package com.jamjam.bookjeok.domains.question.service;

import com.jamjam.bookjeok.domains.question.dto.QuestionDTO;
import com.jamjam.bookjeok.domains.question.dto.QuestionListDTO;
import com.jamjam.bookjeok.domains.question.dto.request.QuestionAnswerRequest;
import com.jamjam.bookjeok.domains.question.dto.response.QuestionAnswerResponse;
import com.jamjam.bookjeok.domains.question.entity.QuestionAnswer;
import com.jamjam.bookjeok.domains.question.repository.QuestionAnswerRepository;
import com.jamjam.bookjeok.domains.question.repository.mapper.QuestionMapper;
import com.jamjam.bookjeok.exception.question.InconsistentQuestionException;
import com.jamjam.bookjeok.exception.question.NotFoundQuestionException;
import com.jamjam.bookjeok.exception.question.QuestionErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionAdminServiceImpl implements QuestionAdminService{

    private final QuestionMapper questionMapper;
    private final QuestionAnswerRepository questionAnswerRepository;

    @Override
    public List<QuestionListDTO> findQuestions(Map<String, Object> params) {

        return questionMapper.findQuestions(params);

    }

    @Override
    public QuestionDTO findQuestionByQuestionId(Map<String, Object> params) {

        return questionMapper.findQuestionByQuestionId(params);

    }

    @Override
    public QuestionAnswerResponse registQuestionAnswer(QuestionAnswerRequest request) {

        QuestionAnswer answer = QuestionAnswer.builder()
                .questionId(request.questionId())
                .writerUid(request.writerUid())
                .contents(request.contents())
                .build();

        QuestionAnswer newAnswer = questionAnswerRepository.save(answer);

        return buildQuestionAnswerResponse(newAnswer);
    }

    @Override
    public QuestionAnswerResponse modifyQuestionAnswer(Long answerId, QuestionAnswerRequest request) {

        Optional<QuestionAnswer> findAnswer = questionAnswerRepository.findByAnswerId(answerId);

        if(findAnswer.isEmpty()) {
            throw new NotFoundQuestionException(QuestionErrorCode.NOTFOUND_ANSWER);
        }

        if(!Objects.equals(findAnswer.get().getQuestionId(), request.questionId())) {
            throw new InconsistentQuestionException(QuestionErrorCode.INCONSISTENT_ANSWER);
        }

        findAnswer.get().updateAnswer(
                request.contents(),
                LocalDateTime.now()
        );

        QuestionAnswer modAnswer = questionAnswerRepository.save(findAnswer.get());

        return buildQuestionAnswerResponse(modAnswer);
    }

    @Override
    public QuestionAnswerResponse deleteQuestionAnswer(Long answerId) {

        Optional<QuestionAnswer> findAnswer = questionAnswerRepository.findByAnswerId(answerId);

        if(findAnswer.isEmpty()) {
            throw new NotFoundQuestionException(QuestionErrorCode.NOTFOUND_ANSWER);
        }

        findAnswer.get().deleteAnswer(
                LocalDateTime.now(),
                true
        );

        QuestionAnswer deleteAnswer = questionAnswerRepository.save(findAnswer.get());

        return buildQuestionAnswerResponse(deleteAnswer);
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
