package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.book.command.entity.Author;
import com.jamjam.bookjeok.domains.book.command.repository.AuthorRepository;
import com.jamjam.bookjeok.domains.member.command.dto.request.InterestAuthorRequest;
import com.jamjam.bookjeok.domains.member.command.entity.InterestAuthor;
import com.jamjam.bookjeok.domains.member.command.entity.InterestAuthorId;
import com.jamjam.bookjeok.domains.member.command.entity.Member;
import com.jamjam.bookjeok.domains.member.command.repository.InterestAuthorRepository;
import com.jamjam.bookjeok.domains.member.command.repository.MemberRepository;
import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import com.jamjam.bookjeok.exception.member.MemberException;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AlreadyInterestedAuthorException;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AuthorNotFoundException;
import com.jamjam.bookjeok.exception.member.interestAuthorException.InterestAuthorLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterestAuthorCommandServiceImpl implements InterestAuthorCommandService{

    private final InterestAuthorRepository interestAuthorRepository;
    private final MemberRepository memberRepository;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public String createInterestAuthor(InterestAuthorRequest interestAuthorRequest){

        // 멤버의 id로 멤버 데려오기 (멤버의 uid를 가져오기 위함)
        Member member = memberRepository.findByMemberId(interestAuthorRequest.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_EXIST_MEMBER));

        // 멤버의 uid 가져오기
        Long memberUid = member.getMemberUid();

        // 특정 회원의 등록된 관심 작가 수 확인하기
        int totalInterestAuthor =
                interestAuthorRepository.countInterestAuthorsByMemberUid(memberUid);

        // 작가의 인원이 30명이라면 추가할 수 없음
        if(totalInterestAuthor == 30){
            throw new InterestAuthorLimitExceededException(MemberErrorCode.INTEREST_AUTHOR_LIMIT_EXCEEDED);
        }

        // 작가 가져오기 (작가의 아이디를 가져오기 위해서 가져오는 것)
        Author author = authorRepository.findByAuthorName(interestAuthorRequest.getAuthorName())
                .orElseThrow(() -> new AuthorNotFoundException(MemberErrorCode.NOT_FOUND_AUTHOR));

        // 작가의 아이디와 멤버 uid 전달하기
        InterestAuthorId newInterestAuthorId =
                new InterestAuthorId(author.getAuthorId(), memberUid);

        // 이미 관심 작가가 등록되어 있는 경우 예외 처리하기
        if(interestAuthorRepository.existsById(newInterestAuthorId)){
            throw new AlreadyInterestedAuthorException(MemberErrorCode.ALREADY_INTERESTED_AUTHOR);
        }

        InterestAuthor newInterestAuthor = InterestAuthor.builder()
                .interestAuthorId(newInterestAuthorId)
                .build();

        interestAuthorRepository.save(newInterestAuthor);

        return interestAuthorRequest.getAuthorName();
    }

    @Override
    @Transactional
    public void deleteInterestAuthor(String userId, Long authorId) {
        Member member = memberRepository.findByMemberId(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_EXIST_MEMBER));

        InterestAuthorId interestAuthorId =
                new InterestAuthorId(authorId, member.getMemberUid());

        InterestAuthor interestAuthor = interestAuthorRepository.findById(interestAuthorId)
                .orElseThrow(() -> new AuthorNotFoundException(MemberErrorCode.NOT_REGIST_AUTHOR));

        interestAuthorRepository.delete(interestAuthor);
    }

}
