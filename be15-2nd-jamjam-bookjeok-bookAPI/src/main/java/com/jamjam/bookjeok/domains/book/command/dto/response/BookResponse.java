package com.jamjam.bookjeok.domains.book.command.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookResponse(Long publisherId, Long categoryId, String bookName, String isbn,
                           LocalDate publishedAt, int price, int stockQuantity, String imageUrl, String bookInfo) {}

