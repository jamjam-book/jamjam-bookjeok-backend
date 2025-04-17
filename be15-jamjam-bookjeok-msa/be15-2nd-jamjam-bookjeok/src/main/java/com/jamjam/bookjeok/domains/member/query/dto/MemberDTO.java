package com.jamjam.bookjeok.domains.member.query.dto;

import com.jamjam.bookjeok.domains.member.command.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Long memberUid;
    private String memberId;
    private String memberName;
    private String phoneNumber;
    private String email;
    private String nickname;
    private String birthDate;
    private boolean marketingConsent = true;
    private MemberRole role = MemberRole.MEMBER;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String activityStatus;

}
