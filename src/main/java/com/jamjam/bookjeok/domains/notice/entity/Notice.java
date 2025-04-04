package com.jamjam.bookjeok.domains.notice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "notices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long writerUid;

    @Column
    private String title;

    @Column // type = text
    private String contents;

    @Column
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;

    @Column
    @ColumnDefault("0")
    private Boolean isDeleted;


}
