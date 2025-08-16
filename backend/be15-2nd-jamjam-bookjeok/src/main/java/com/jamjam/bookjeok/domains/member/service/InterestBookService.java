package com.jamjam.bookjeok.domains.member.service;

import com.jamjam.bookjeok.common.dto.Pagination;
import com.jamjam.bookjeok.domains.book.entity.Book;
import com.jamjam.bookjeok.domains.book.repository.BookRepository;
import com.jamjam.bookjeok.domains.member.dto.request.InterestBookRequest;
import com.jamjam.bookjeok.domains.member.dto.request.PageRequest;
import com.jamjam.bookjeok.domains.member.dto.InterestBookDTO;
import com.jamjam.bookjeok.domains.member.dto.response.InterestBookListResponse;
import com.jamjam.bookjeok.domains.member.entity.InterestAuthor;
import com.jamjam.bookjeok.domains.member.entity.InterestAuthorId;
import com.jamjam.bookjeok.domains.member.entity.InterestBook;
import com.jamjam.bookjeok.domains.member.entity.InterestBookId;
import com.jamjam.bookjeok.domains.member.repository.mapper.InterestBookMapper;
import com.jamjam.bookjeok.domains.member.repository.repository.InterestBookRepository;
import com.jamjam.bookjeok.exception.member.MemberErrorCode;
import com.jamjam.bookjeok.exception.member.interestAuthorException.AuthorNotFoundException;
import com.jamjam.bookjeok.exception.member.interestBookException.AlreadyInterestedBookException;
import com.jamjam.bookjeok.exception.member.interestBookException.InterestBookLimitExceededException;
import com.jamjam.bookjeok.exception.member.interestBookException.NotFoundBookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterestBookService {

    private final InterestBookMapper interestBookMapper;
    private final BookRepository bookRepository;
    private final InterestBookRepository interestBookRepository;

    @Transactional(readOnly = true)
    public InterestBookListResponse getInterestBookListByMemberId(
            String memberId,
            PageRequest pageRequest
    ){
        List<InterestBookDTO> interestBookList = interestBookMapper.findInterestBookList(memberId,pageRequest);

        long totalBooks = interestBookList.size();

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();

        return InterestBookListResponse.builder()
                .interestBookList(interestBookList)
                .pagination(Pagination.builder()
                        .currentPage(page)
                        .totalPage((int)Math.ceil((double)totalBooks/size))
                        .totalItems(totalBooks)
                        .build())
                .build();
    }

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
