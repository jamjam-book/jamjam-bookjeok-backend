package com.jamjam.bookjeok.domains.book.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PriceRangeDTO {
    int min;
    int max;
}
