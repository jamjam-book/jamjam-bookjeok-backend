package com.jamjam.bookjeok.domains.member.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class InterestAuthorDTO {

    private String authorName;
    private List<BookNameDTO> bookList;

}
