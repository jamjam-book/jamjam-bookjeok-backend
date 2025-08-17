package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorOthersSearchCondition {
    private Long bookId;             // 제외할 도서 ID
    private List<Long> authorIds;    // 저자 ID 리스트
    private int limit = 10;          // 최대 개수 (기본값 10)
}
