package com.jamjam.bookjeok.domains.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "interest_books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestBook {

    @Id @EmbeddedId
    private InterestBookId interestBookId;

    @Builder
    public InterestBook(InterestBookId interestBookId){
        this.interestBookId = interestBookId;
    }

}