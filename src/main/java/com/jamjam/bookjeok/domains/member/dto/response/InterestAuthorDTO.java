package com.jamjam.bookjeok.domains.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Setter
@NoArgsConstructor
public class InterestAuthorDTO {

    private String authorName;
    private List<BookNameDTO> bookList;

}
