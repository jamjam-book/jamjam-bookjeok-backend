package com.jamjam.bookjeok.domains.member.command.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "interest_authors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestAuthor {

    @Id @EmbeddedId
    private InterestAuthorId interestAuthorId;

    @Builder
    public InterestAuthor(InterestAuthorId interestAuthorId){
        this.interestAuthorId = interestAuthorId;
    }

}