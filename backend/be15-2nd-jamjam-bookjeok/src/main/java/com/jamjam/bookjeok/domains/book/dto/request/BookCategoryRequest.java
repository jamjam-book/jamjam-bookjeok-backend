package com.jamjam.bookjeok.domains.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BookCategoryRequest (@NotBlank String categoryName){}
