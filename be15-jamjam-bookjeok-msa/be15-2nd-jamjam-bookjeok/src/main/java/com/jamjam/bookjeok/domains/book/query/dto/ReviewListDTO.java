package com.jamjam.bookjeok.domains.book.query.dto;

import com.jamjam.bookjeok.common.dto.Pagination;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewListDTO {
    private List<ReviewDTO> reviews;
    private Pagination pagination;
}
