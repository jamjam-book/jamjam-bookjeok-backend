package com.jamjam.bookjeok.domains.member.command.service;

import com.jamjam.bookjeok.domains.book.command.entity.Book;
import com.jamjam.bookjeok.domains.book.command.repository.BookRepository;
import com.jamjam.bookjeok.domains.member.command.dto.request.InterestBookRequest;
import com.jamjam.bookjeok.domains.member.command.entity.InterestBook;
import com.jamjam.bookjeok.domains.member.command.entity.InterestBookId;
import com.jamjam.bookjeok.domains.member.command.repository.InterestBookRepository;
import com.jamjam.bookjeok.domains.member.query.mapper.InterestBookMapper;
import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import com.jamjam.bookjeok.exception.member.interestBookException.AlreadyInterestedBookException;
import com.jamjam.bookjeok.exception.member.interestBookException.InterestBookLimitExceededException;
import com.jamjam.bookjeok.exception.member.interestBookException.NotFoundBookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestBookCommandServiceImpl implements InterestBookCommandService{

    private final BookRepository bookRepository;
    private final InterestBookRepository interestBookRepository;
    private final InterestBookMapper interestBookMapper;

    @Override
    @Transactional
    public String createInterestBook(
            Long memberUid, // 로그인 부분 완성 되면 제거 -> 로그인 된 사용자를 가져오면 됨
            InterestBookRequest interestBookRequest
    ){

        int totalInterestedBook = interestBookMapper.countInterestBookByMemberUid(memberUid);

        log.info("관심도서의 수는 : {}", totalInterestedBook);

        // 관심도서는 최대 30권까지
        if(totalInterestedBook == 30){
            throw new InterestBookLimitExceededException(MemberErrorCode.INTEREST_BOOK_LIMIT_EXCEEDED);
        }

        // 만약 없는 도서라면 예외 발생시키기
        Book book = bookRepository.findBookByBookId(interestBookRequest.getBookId())
                .orElseThrow(() -> new NotFoundBookException(MemberErrorCode.NOT_FOUND_BOOK));

        InterestBookId interestBookId
                = new InterestBookId(interestBookRequest.getBookId(), memberUid);

        if(interestBookRepository.existsById(interestBookId)){
            throw new AlreadyInterestedBookException(MemberErrorCode.ALREADY_INTERESTED_BOOK);
        }

        InterestBook interestBook = InterestBook.builder()
                .interestBookId(interestBookId)
                .build();

        interestBookRepository.save(interestBook);

        log.info("책이 저장 됨");

        return book.getBookName();
    }

    @Override
    @Transactional
    public void deleteInterestBook(
            Long memberUid, // 로그인 부분 완성 되면 제거 -> 로그인 된 사용자를 가져오면 됨
            InterestBookRequest interestBookRequest
    ){
        Book book = bookRepository.findBookByBookId(interestBookRequest.getBookId())
                .orElseThrow(() -> new NotFoundBookException(MemberErrorCode.NOT_FOUND_BOOK));

        InterestBookId interestBookId
                = new InterestBookId(interestBookRequest.getBookId(), memberUid);

        InterestBook interestBook = interestBookRepository.findById(interestBookId)
                .orElseThrow(() -> new NotFoundBookException(MemberErrorCode.NOT_REGIST_INTEREST_BOOK));

        interestBookRepository.delete(interestBook);
    }

}
