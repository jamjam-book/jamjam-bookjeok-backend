package com.jamjam.bookjeok.domains.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {

    private Long publisherId;
    private String publisherName;

    public PublisherDTO(String publisherName) {
        this.publisherName = publisherName;
    }
}
